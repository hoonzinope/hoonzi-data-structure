# Hoonzi Data Structures

Redis 등의 시스템에서 사용되는 자료구조를 직접 구현해 보며 내부 동작과 시간 복잡도를 공부하는 학습용 저장소입니다.

현재는 Java와 Gradle을 사용해 Skip List를 구현하고 있습니다. API와 내부 구조는 학습 과정에서 계속 바뀔 수 있습니다.

## 현재 구현

- `SkipList<E>`
  - `Comparator`를 이용한 제네릭 정렬
  - 노드별 무작위 레벨 생성
  - 레벨별 forward link 연결
  - 삽입, 삭제, 전체 레벨 순회
  - 중복 값의 개수를 `Node.count`로 관리
- 최대 레벨: `16`
- `search(E)`는 아직 구현 예정
- 테스트 코드는 아직 추가 예정

중복 값을 허용하는 현재 구현에서 `size`는 서로 다른 값의 개수가 아니라 삽입된 원소의 총 개수를 의미합니다.

## 실행 환경

- JDK 17 이상
- Gradle Wrapper 9.6.0

`java.util.random.RandomGenerator`를 사용하므로 JDK 17 이상이 필요합니다.

## 빌드 및 테스트

macOS/Linux에서는 다음 명령을 실행합니다.

```bash
./gradlew clean build
./gradlew test
```

Windows에서는 `gradlew.bat`을 사용합니다.

```bat
gradlew.bat clean build
gradlew.bat test
```

## 예제 실행

현재 예제 진입점은 `MainClass`입니다.

```bash
./gradlew classes
java -cp build/classes/java/main MainClass
```

실행할 때마다 무작위 레벨과 입력 값이 사용되므로 출력 결과가 달라질 수 있습니다.

## 프로젝트 구조

```text
.
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── gradle/wrapper/
└── src/main/java/
    ├── MainClass.java
    └── structure/skiplist/
        ├── Node.java
        └── SkipList.java
```

## 학습 로드맵

1. `search` 구현 및 삽입/삭제 경로 재사용
2. 중복을 허용하는 `SkipListMultiset`과 중복을 금지하는 `SkipListSet`의 정책 분리
3. JUnit 기반의 경계값·랜덤 테스트 추가
4. 재현 가능한 테스트를 위해 난수 생성기 주입
5. 다른 정렬 자료구조와 시간 복잡도 및 성능 비교
6. 해시 테이블, 연결 리스트, 트리 등 다른 자료구조 추가

## 참고

이 저장소의 구현은 학습 목적이며, 동시성·메모리 최적화·운영 환경의 안정성은 보장하지 않습니다.
