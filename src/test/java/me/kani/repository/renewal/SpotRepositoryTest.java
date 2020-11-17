package me.kani.repository.renewal;

import me.kani.Connector;
import me.kani.entity.legacy.model.ParsedUrlDto;
import me.kani.entity.legacy.types.UrlKind;
import me.kani.entity.renewal.SpotEntity;
import me.kani.entity.renewal.types.Category;
import me.kani.repository.legacy.*;
import me.kani.utility.UrlUtility;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SpotRepositoryTest {
    private final Connection legacyConnection = new Connector("localhost", 3306, "kokkok_legacy",
            "root", "rootpass").getConnection();
    private final Connection renewalConnection = new Connector("localhost", 3306, "kokkok",
            "root", "rootpass").getConnection();

    @BeforeEach
    public void beginTransaction() throws SQLException {
        renewalConnection.setAutoCommit(false);
    }

    @AfterEach
    public void endTransaction() throws SQLException {
        renewalConnection.commit();
    }

    @Test
    @DisplayName("지역 장소 데이터 가져오기")
    public void findAllTest() {
        final var spotRepository = new SpotRepository(renewalConnection);

        System.out.println(spotRepository.findAll().size());
    }

    @Test
    @Disabled
    @DisplayName("지역 명소 데이터 보정 작업")
    public void areaToSpotTest() throws SQLException {
        final var areaRepository = new AreaRepository(legacyConnection);
        final var spotRepository = new SpotRepository(renewalConnection);

        final var areaEntities = areaRepository.findAll();

        System.out.println("AreaEntities size: " + areaEntities.size());

        final var accessibleUrls = getAccessibleUrls(UrlKind.AREA);

        LocalDateTime now = LocalDateTime.now();
        String area = "충청북도";

        var spotEntities = areaEntities.stream()
                .map(e ->
                        new SpotEntity(null, makeFullAreaName(area, e.areaNo().substring(0, 2)),
                            Category.convertFromAreaKind(e.areaKind()), e.name(), e.address(),
                            e.latitude(), e.longitude(), e.intro(), accessibleUrls.getOrDefault(e.areaNo(), null), null,
                            now, 1L, now, 1L))
                .collect(Collectors.toList());

        System.out.println("SpotEntities size: " + spotEntities.size());

        spotRepository.saveAll(spotEntities);
        renewalConnection.rollback();
    }

    @Test
    @Disabled
    @DisplayName("쇼핑 데이터 보정 작업")
    public void shoppingToSpotTest() throws SQLException {
        final var shoppingRepository = new ShoppingRepository(legacyConnection);
        final var spotRepository = new SpotRepository(renewalConnection);

        final var shoppingEntities = shoppingRepository.findAll();

        System.out.println("ShoppingEntities size: " + shoppingEntities.size());

        final var accessibleUrls = getAccessibleUrls(UrlKind.SHOPPING);

        LocalDateTime now = LocalDateTime.now();
        String area = "충청북도";

        var spotEntities = shoppingEntities.stream()
                .map(e ->
                        new SpotEntity(null, makeFullAreaName(area, e.shopNo().substring(0, 2)),
                                Category.SHOPPING, e.name(), e.address(),
                                e.latitude(), e.longitude(), e.intro(), accessibleUrls.getOrDefault(e.shopNo(), null), null,
                                now, 1L, now, 1L))
                .collect(Collectors.toList());

        System.out.println("SpotEntities size: " + spotEntities.size());

        spotRepository.saveAll(spotEntities);
        renewalConnection.rollback();
    }

    @Test
    @Disabled
    @DisplayName("숙박 데이터 보정 작업")
    public void accommodationToSpotTest() throws SQLException {
        final var accommodationRepository = new AccommodationRepository(legacyConnection);
        final var spotRepository = new SpotRepository(renewalConnection);

        final var accommodationEntities = accommodationRepository.findAll();

        System.out.println("AccommodationEntities size: " + accommodationEntities.size());

        final var accessibleUrls = getAccessibleUrls(UrlKind.ACCOMMODATION);

        LocalDateTime now = LocalDateTime.now();
        String area = "충청북도";

        var spotEntities = accommodationEntities.stream()
                .map(e ->
                        new SpotEntity(null, makeFullAreaName(area, e.accNo().substring(0, 2)),
                                Category.ACCOMMODATION, e.name(), e.address(),
                                e.latitude(), e.longitude(), e.intro(), accessibleUrls.getOrDefault(e.accNo(), null), null,
                                now, 1L, now, 1L))
                .collect(Collectors.toList());

        System.out.println("SpotEntities size: " + spotEntities.size());

        spotRepository.saveAll(spotEntities);
        renewalConnection.rollback();
    }

    @Test
    @Disabled
    @DisplayName("식당 데이터 보정 작업")
    public void restaurantToSpotTest() throws SQLException {
        final var restaurantRepository = new RestaurantRepository(legacyConnection);
        final var spotRepository = new SpotRepository(renewalConnection);

        final var restaurantEntities = restaurantRepository.findAll();

        System.out.println("RestaurantEntities size: " + restaurantEntities.size());

        final var accessibleUrls = getAccessibleUrls(UrlKind.RESTAURANT);

        LocalDateTime now = LocalDateTime.now();
        String area = "충청북도";

        var spotEntities = restaurantEntities.stream()
                .map(e ->
                        new SpotEntity(null, makeFullAreaName(area, e.rstNo().substring(0, 2)),
                                Category.RESTAURANT, e.name(), e.address(),
                                e.latitude(), e.longitude(), e.intro(), accessibleUrls.getOrDefault(e.rstNo(), null), null,
                                now, 1L, now, 1L))
                .collect(Collectors.toList());

        System.out.println("SpotEntities size: " + spotEntities.size());

        spotRepository.saveAll(spotEntities);
        renewalConnection.rollback();
    }

    private Map<String, String> getAccessibleUrls(UrlKind urlKind) {
        final var urlRepository = new UrlRepository(legacyConnection);

        long start = System.currentTimeMillis();

        final var accessibleUrls = urlRepository.findAll()
                .stream()
                .filter(e -> e.kind() == urlKind)
                .flatMap(e ->
                        extractUrlFromText(e.address()).stream()
                                .filter(UrlUtility::isAccessibleUrl)
                                .map(s -> new ParsedUrlDto(e.itemId(), s)))
                .collect(Collectors.groupingBy(ParsedUrlDto::itemId))
                .entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(),
                        e.getValue().stream()
                                .map(ParsedUrlDto::address)
                                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                                .collect(Collectors.toList())
                                .get(0)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("It takes " + (System.currentTimeMillis() - start) + "ms" + ", AccessibleUrl size: " + accessibleUrls.size());

        return accessibleUrls;
    }

    @Test
    @DisplayName("문자열에서 url 추출")
    public void extractUrlFromTextTest() {
        List<String> addresses = List.of(
                """
                    <a href="http://www.cjlarvaland.co.kr/home" target="_blank" title="충주 라바랜드 이동">
                    http://www.cjlarvaland.co.kr/home/</a>
                """,
                """
                    충북나드리 <a href="http://www.chungbuknadri.net?search=q:text&page=1" target="_blank" title="새창 : 충북나드리 홈페이지로 이동">http://www.chungbuknadri.net</a>
                """,
                """
                    청주 문화관광 <a href="http://www.cheongju.go.kr/tour/index.do" target="_blank" title="새창: 청주시 문화관광 홈페이지로 이동">http://www.cheongju.go.kr</a>  <br />문화재청 <a href="http://www.cha.go.kr" target="_blank" title="새창: 문화재청 홈페이지로 이동">http://www.cha.go.kr</a>]
                """,
                """
                    <a href="http://www.bokcheo-nam.or.kr/" target="_blank" title="새창 : 복천암 홈페이지로 이동">http://www.bokcheonam.or.kr</a>
                """,
                """
                    분저리녹색체험마을 <a href="http://www.분저리녹색체험마을.kr" target="_blank" title="분저리녹색체험마을 사이트로 이동">http://www.분저리녹색체험마을.kr</a>
                """,
                """
                    <a href="http://사격장.kr" target="_blank" title="새창 : 단양클레이사격장 홈페이지로 이동">http://사격장.kr </a>
                """
        );
        List<Set<String>> expected = List.of(
            Set.of("http://www.cjlarvaland.co.kr/home"),
            Set.of("http://www.chungbuknadri.net?search=q:text&page=1", "http://www.chungbuknadri.net"),
            Set.of("http://www.cheongju.go.kr/tour/index.do", "http://www.cheongju.go.kr", "http://www.cha.go.kr"),
            Set.of("http://www.bokcheo-nam.or.kr", "http://www.bokcheonam.or.kr"),
            Set.of("http://www.분저리녹색체험마을.kr"),
            Set.of("http://사격장.kr")
        );

        List<Set<String>> result = addresses.stream()
                .map(this::extractUrlFromText)
                .collect(Collectors.toList());

        assertTrue(CollectionUtils.isEqualCollection(expected, result));
    }

    private Set<String> extractUrlFromText(String text) {
        Set<String> containedUrls = new HashSet<>();
//        String urlRegex = "(?:^|[\\W])((ht|f)tp(s?)://|(www\\.)(([\\w\\-]+\\.)+?([\\w\\-.~]+/?)*[\\p{javaAlphabetic}.,%_=?&#\\-+()\\[\\]*$~@!:/{};']*)";
        String urlRegex = "\\(?\\b(http(s)?://|www[.])[-\\p{LD}+&@#/%?=~_()|!:,.;]*[\\p{Alnum}+&@#/%=~_()|]";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            int startIndex = matcher.start(1);
            int endIndex = matcher.end();
            String extractUrl = text.substring(startIndex, endIndex);
            containedUrls.add(extractUrl.endsWith("/")? extractUrl.substring(0, extractUrl.length() - 1): extractUrl);
        }

        return containedUrls;
    }

    @Test
    @DisplayName("짧은 지역이름 풀네임으로 만들기")
    public void makeFullAreaNameTest() {
        List<String> shortAreaNames = List.of("충주", "청주", "괴산");
        String area = "충청북도";

        List<String> expected = List.of("충청북도 충주시", "충청북도 청주시", "충청북도 괴산군");

        List<String> fullAreaNames = shortAreaNames.stream()
                .map(s -> makeFullAreaName(area, s))
                .collect(Collectors.toList());

        assertIterableEquals(expected, fullAreaNames);
    }

    private String makeFullAreaName(String area, String shortAreaName) {
        StringBuilder areaName = new StringBuilder(area);
        areaName.append(" ");
        areaName.append(shortAreaName);

        switch (shortAreaName) {
            case "괴산", "단양", "보은", "영동", "옥천", "음성", "증평", "진천" -> areaName.append("군");
            case "제천", "청주", "충주" -> areaName.append("시");
        }

        return areaName.toString();
    }
}