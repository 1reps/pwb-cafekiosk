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
Entity는 BaseEntity를 상속받아 조회 시 필요없는 날짜 정보를 포함하고있다.  
필요한 정보만 추출하기 위해 Response 객체로 데이터 가공

---
