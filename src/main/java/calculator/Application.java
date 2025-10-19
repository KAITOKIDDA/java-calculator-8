package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.println("덧셈할 문자열을 입력해 주세요.");


            // 사용자 입력 값 받기
            String input = Console.readLine();

            // 핵심 로직은 StringAddCalculator 클래스에 위임
            StringAddCalculator calculator = new StringAddCalculator();
            int result = calculator.add(input);

            // 입출력 요구 사항에 따른 출력 형식
            System.out.println("결과 : " + result);


    }
}
