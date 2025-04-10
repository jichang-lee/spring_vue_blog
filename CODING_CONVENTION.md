# 코딩 컨벤션 가이드

이 문서는 spring_vue_blog 프로젝트의 코딩 컨벤션을 정의합니다.

## 백엔드 (Java/Spring) 컨벤션

### 네이밍 컨벤션
- **클래스**: PascalCase (예: `BoardController`, `UserService`)
- **메소드/변수**: camelCase (예: `getUserById`, `postComment`)
- **상수**: UPPER_SNAKE_CASE (예: `MAX_RETRY_COUNT`)
- **패키지**: lowercase (예: `com.blog.service`)

### 코드 포맷팅
- 들여쓰기: 4칸 스페이스
- 최대 줄 길이: 120자
- 메소드 길이: 최대 50줄 권장
- 한 클래스 당 최대 700줄 권장

### 주석
- 클래스, 인터페이스: JavaDoc 스타일 주석 (`/** ... */`)
- 복잡한 로직이나 비즈니스 규칙에 대한 설명 추가
- 명확한 코드는 주석 지양

### REST API 설계
- URI는 명사형 사용 (예: `/api/posts`, `/api/users`)
- HTTP 메소드 적절히 사용 (GET, POST, PUT, DELETE)
- 응답 상태 코드 일관성 유지 (200, 201, 400, 404, 500 등)

### 예외 처리
- 구체적인 예외 클래스 사용
- 전역 예외 핸들러 활용
- 사용자에게 적절한 오류 메시지 제공

## 프론트엔드 (Vue3) 컨벤션

### 네이밍 컨벤션
- **컴포넌트**: PascalCase (예: `UserProfile.vue`, `BoardList.vue`)
- **Props**: camelCase
- **메소드/변수**: camelCase
- **CSS 클래스**: kebab-case (예: `user-profile`, `post-list`)

### 코드 포맷팅
- 들여쓰기: 2칸 스페이스
- 최대 줄 길이: 100자
- 세미콜론 사용
- 작은따옴표(') 사용 (vs 큰따옴표)

### 컴포넌트 구조
- 컴포넌트 이름은 항상 다중 단어로 구성 (예: `UserList`, `PostDetail`)
- props는 타입과 기본값 명시
- computed 속성과 methods 적절히 구분하여 사용
- 컴포넌트 파일은 template, script, style 순서로 구성

### 상태 관리
- Pinia/Vuex 상태는 모듈로 구성
- 상태 변경은 mutations/actions을 통해서만 수행
- 비동기 작업은 actions에서 처리

## 공통 컨벤션

### 버전 관리
- 의미 있는 커밋 메시지 작성
- 커밋 메시지 형식: `[타입]: 설명` (예: `[feat]: 게시판 페이징 기능 추가`)
- 타입 종류: feat, fix, docs, style, refactor, test, chore 등
- 브랜치 네이밍: `feature/기능명`, `bugfix/버그명`, `hotfix/핫픽스명`

### 코드 리뷰
- PR 제출 전 셀프 리뷰 수행
- 코드 중복 최소화
- 불필요한 주석 제거
- 테스트 코드 작성 권장
