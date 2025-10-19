package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringAddCalculator {

    // 커스텀 구분자 형식 (//(단일 문자)\n(숫자열))을 파싱하기 위한 패턴
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.)\\R(.*)");
    // 기본 구분자는 쉼표 또는 콜론
    private static final String DEFAULT_DELIMITERS = "[,:]";

    public int add(String text) {
        // 기능 요구 사항: "" => 0
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // 1. 구분자에 따라 문자열을 분리하고 숫자 목록을 얻음
        List<Integer> numbers = splitAndParse(text);

        // 2. 숫자 목록의 합계를 계산
        return sum(numbers);
    }

    private List<Integer> splitAndParse(String text) {
        String delimiterRegex = DEFAULT_DELIMITERS;
        String numbersString = text;

        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(text);

        // 기능 요구 사항: 커스텀 구분자 처리
        if (matcher.matches()) {
            // 커스텀 구분자 추출 및 정규식 특수문자 이스케이프 처리 (Pattern.quote)
            String customDelimiter = Pattern.quote(matcher.group(1));
            delimiterRegex = customDelimiter;
            numbersString = matcher.group(2); // 실제 숫자 부분
        }

        // 스트림을 이용해 분리, 공백/빈 문자열 제거, 정수 변환 및 검증
        return Arrays.stream(numbersString.split(delimiterRegex))
                .map(s -> s.trim()) // 공백 제거
                .filter(s -> !s.isEmpty()) // 빈 문자열 필터링 (ex: "1,,2" 같은 경우를 방지)
                .map(this::toPositiveInt) // 양수 정수로 변환 및 예외 검증
                .collect(Collectors.toList());
    }

    private int toPositiveInt(String s) {
        try {
            int number = Integer.parseInt(s);

            // 기능 요구 사항: 음수 입력 시 IllegalArgumentException 발생
            if (number < 0) {
                throw new IllegalArgumentException("[ERROR] 음수는 입력할 수 없습니다.");
            }
            return number;
        } catch (NumberFormatException e) {
            // 기능 요구 사항: 숫자가 아닌 문자 입력 시 IllegalArgumentException 발생
            throw new IllegalArgumentException("[ERROR] 유효한 숫자가 아닙니다.");
        }
    }

    private int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }
}