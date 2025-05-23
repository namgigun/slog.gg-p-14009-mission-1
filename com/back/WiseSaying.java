package com.back;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WiseSaying {
    static Scanner sc = new Scanner(System.in);

    static int lastId = 1; // 다음에 사용할 Id

    static class Words {  // 명언 클래스
        String wiseSay;
        String writer;

        public Words(String wiseSay, String writer) {
            this.wiseSay = wiseSay;
            this.writer = writer;
        }
    }

    static String regex = ".*[!@#$%^&*()\\-_=+\\[\\]{}|\\\\;:'\",.<>/?].*";


    static Map<Integer, Words> list = new HashMap<>(); // 명언 저장 레포지토리 <id, 명언정보>
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");

        // 명령입력
        label:
        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            switch (cmd) {
                case "종료":
                    break label;
                case "등록":
                    enroll();
                    break;
                case "목록":
                    showList();
                    break;
                default:
                    if (cmd.substring(0, 2).equals("삭제")) {
                        delete(cmd);
                    } else if (cmd.substring(0, 2).equals("수정")) {
                        revise(cmd);
                    }
                    break;
            }
        }

        sc.close();
    }

    // 특수문자 체크
    private static boolean isAsterisk(String input) {
        if (input.matches(regex)) {
            System.out.println("특수문자가 포함되었습니다. 다시 입력해주세요.");
            return true;
        }

        return false;
    }

    // 삭제
    private static void delete(String cmd) {
        // 명령어로부터 id 값을 추출
        StringTokenizer st = new StringTokenizer(cmd,"삭제?id=");
        int deleteId = Integer.parseInt(st.nextToken());

        // id 값에 해당하는 정보가 없는 경우
        if(list.get(deleteId) == null) {
            System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
            return;
        }

        // id 값에 해당하는 레포지토리로부터 명언정보를 삭제한다.
        list.remove(deleteId);
        System.out.println(deleteId + "번 명언이 삭제되었습니다.");
    }

    // 수정
    private static void revise(String cmd) {
        // 명령어로부터 id 값을 추출
        StringTokenizer st = new StringTokenizer(cmd,"수정?id=");
        int reviseId = Integer.parseInt(st.nextToken());

        // id 값에 해당하는 정보가 없는 경우
        if(list.get(reviseId) == null) {
            System.out.println(reviseId + "번 명언은 존재하지 않습니다.");
            return;
        }

        // 수정할 정보를 입력
        System.out.println("명언(기존) : " + list.get(reviseId).wiseSay);

        String wiseSay;
        do {
            System.out.print("명언 : ");
            wiseSay = sc.nextLine().trim();
        }  while(isAsterisk(wiseSay));

        System.out.println("작가(기존) : " + list.get(reviseId).writer);

        String writer;
         do {
            System.out.print("작가 : ");
            writer = sc.nextLine().trim();
        } while(isAsterisk(writer));

        list.replace(reviseId, new Words(wiseSay, writer));
    }

    // 목록
    private static void showList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        // 리스트 출력
        for(int i = lastId - 1; i >= 1; i--) {
            Words one = list.get(i);

            // 레포지토리에 존재하지 않는 정보는 출력하지 않는다.
            if(one != null) {
                System.out.println(i + " / "+ one.writer + " / "
                        +  one.wiseSay);
            }
        }
    }

    // 등록
    private static void enroll() {
        // 삽입할 명언 정보를 입력
        String wiseSay;
        do {
            System.out.print("명언 : ");
            wiseSay = sc.nextLine().trim();
        }  while(isAsterisk(wiseSay));

        String writer;
        do {
            System.out.print("작가 : ");
            writer = sc.nextLine().trim();
        } while(isAsterisk(writer));

        // 명언 레포지토리에 입력 정보를 삽입
        list.put(lastId, new Words(wiseSay, writer));
        System.out.println(lastId + "번 명언이 등록되었습니다.");

        lastId++;
    }
}
