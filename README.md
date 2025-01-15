#### Test Driven Development

- RED(실패하는 테스트 작성)
- GREEN(테스트 통과 최소한의 코딩)
- REFACTOR(구현 코드 개선 테스트 통과 유지)
- RED > GREEN > REFACTOR

---

#### BDD, Behavior Driven Development

- TDD에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다,
  시나리오에 기반한 테스트케이스(TC) 자체에 집중하여 테스트한다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의
  추상화 수준(레벨)을 권장

---

#### Given / When / Then

- 어떤 환경에서
- 어떤 행동을 진행했을 때
- 어떤 상태 변화가 일어난다

--- 

#### Layerd Architecture (관심사의 분리, 역할과 책임)

- Presentation
- Business
- Persistence

#### 테스트 하기 복잡해 보인다?

- 테스트 하기 좋은 환경으로 분리하고 역할에 맞는 테스트 작성

--- 

#### 통합 테스트

- 여러 모듈이 협렵하는 기능을 통합적으로 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는
  기능 전체의 신뢰성을 보장할 수 없다.
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트

--- 

#### Response Object

Entity는 BaseEntity를 상속받아 조회 시 필요없는   
날짜 정보를 포함하고있다.  
필요한 정보만 추출하기 위해 Response 객체로 캡슐화 한다.

---

#### Presentation Layer

- 외부 세계의 요청을 가장 먼저 받는 계층
- 파라미터에 대한 최소한의 검증을 수행한다.
- Mocking(Business Layer + Persistence Layer)  
  Presentation Layer에만 집중하기 위하여 Mocking Test

#### Persistence Layer

- Data Acceess의 역할
- 비즈니스 가공 로직이 포함되어서는 안 된다.  
  Data에 대한 CRUD에만 집중한 레이어

#### Business Layer

- 비즈니스 로직을 구현하는 역할
- Persistence Layer와의 상호작용(Data를 읽고 쓰는 행위)을 통해  
  비즈니스 로직을 전개시킨다.
- 트랜잭션을 보장해야 한다.

--- 

#### Repository test

- Persistenct Layer

#### Service test

- Business Layer + Persistence Layer

--- 

#### @DataJpaTest, @SpringBootTest

- @DataJpaTest 자동 Transactional Rollback
- @SpringBootTest @AfterEach를 사용하여 tearDown

---

#### Checked Exception 체크시 유의사항

- Service Layer와 Unit Layer의 Checked Exception 작성시  
  같은 Checked Exception 이라도 다른 관점에서 봐야한다.  
  다른 서비스에서 재사용할 수 있는 메서드일 경우  
  단위 테스트로서의 테스트 관점을 보장해줘야한다.

--- 

#### CQRS

- Command  
  쓰기 전용
- Query 모델  
  읽기 전용(Read Only)
- Service를 분리하는 방식
- Service 전역에 @Transactional(readOnly = true)  
  Command 메서드 영역에만 @Transactional 작성

---

#### Controller Layer DTO와 Service Layer DTO의 의존 관계..?

- Controller가 Service를 주입받아서 호출한다.
- 상위 Layer는 하위 Layer의 호출부이기 때문에 당연히 알고있지만  
  하위 Layer는 상위 Layer가 하는 일을 알고 있는것은 좋지 않을 수 있다.
- Layer 별 DTO를 고려해보자(Clean POJO DTO), 책임분리

--- 

#### Test Double
- Dummy
  - 아무 것도 하지 않는 깡통 객체
- Fake
  - 단순한 형태로 동일한 기능은 수행하나, 프로덕션에서 쓰기에는 부족한 객체
- Stub(State Verification)
  - 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체  
    그 외에는 응답하지 않는다.
- Spy
  - Stub이면서 호출된 내용을 기록하여 보여줄 수 있는 객체  
    일부는 실제 객체처럼 동작시키고 일부만 Stubbing할 수 있다.
- Mock(Behavior Verification)
  - 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

--- 

