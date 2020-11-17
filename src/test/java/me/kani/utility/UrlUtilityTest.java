package me.kani.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UrlUtilityTest {
    @Test
    @DisplayName("접속가능한 Url 확인")
    public void accessibleUrlTest() {
        Set<String> urls = Set.of(
                "http://www.jincheon.go.kr/home/contents/view.do?jinLayoutVal=main.contents.newview&confirm=true&menu_grp_key=1&menu_key=290",
                "http://www.jincheon.go.kr"
        );
        Set<String> expected = Set.of("http://www.jincheon.go.kr");

        Set<String> result = urls.stream()
                .filter(UrlUtility::isAccessibleUrl)
                .collect(Collectors.toSet());

        assertIterableEquals(expected, result);
    }
}