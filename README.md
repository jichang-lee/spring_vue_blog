# Spring Vue Blog

## 프로젝트 개요
Spring Boot와 Vue3를 이용한 블로그 웹 애플리케이션입니다.

### 기술 스택
- **백엔드**: Java 17, Spring Boot 3.x, JPA, MySQL
- **프론트엔드**: Vue 3, JavaScript/TypeScript

## 코드 품질 관리

이 프로젝트는 코드 품질을 유지하기 위해 자동화된 코드 검사 시스템을 사용합니다.

### 코딩 컨벤션

모든 코드는 [코딩 컨벤션 문서](./CODING_CONVENTION.md)에 정의된 규칙을 따릅니다.

### 자동 코드 검사

PR(Pull Request)이 생성되면 다음과 같은 자동 검사가 실행됩니다:

1. **백엔드 코드 검사**
   - Checkstyle을 사용하여 Java 코드 스타일 검사
   - 코드 품질 보고서 생성

2. **프론트엔드 코드 검사**
   - ESLint를 사용하여 JavaScript/Vue 코드 스타일 검사
   - Prettier를 사용하여 코드 포맷팅 검사
   
3. **통합 코드 분석**
   - 코드 변경 통계 (파일 수, 라인 수 등)
   - 잠재적 보안 이슈 검사
   - PR 요약 리포트 생성

### GitHub Actions 상태 뱃지
![Backend Code Check](https://github.com/jichang-lee/spring_vue_blog/actions/workflows/backend-check.yml/badge.svg)
![Frontend Code Check](https://github.com/jichang-lee/spring_vue_blog/actions/workflows/frontend-check.yml/badge.svg)
![PR Code Review](https://github.com/jichang-lee/spring_vue_blog/actions/workflows/pr-code-review.yml/badge.svg)

## 시작하기

### 백엔드 실행
```bash
./gradlew bootRun
```

### 프론트엔드 실행
```bash
cd front
npm install
npm run serve
```

## 기여 가이드

1. 프로젝트를 포크합니다.
2. 새로운 기능 브랜치를 만듭니다 (`git checkout -b feature/amazing-feature`).
3. 변경사항을 커밋합니다 (`git commit -m 'Add some amazing feature'`).
4. 브랜치에 푸시합니다 (`git push origin feature/amazing-feature`).
5. Pull Request를 생성합니다.

모든 PR은 자동화된 코드 검사를 통과해야 합니다.
