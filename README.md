# shop

Spring Boot 4.1.1 / Java 25 / Gradle 기반 프로젝트입니다.

## Dev container로 열기

### 필요한 것

- Docker Desktop
- VS Code
- VS Code의 Dev Containers 확장

### 여는 순서

1. 저장소를 clone하고 폴더를 VS Code로 엽니다.
2. 오른쪽 아래 알림 또는 명령 팔레트(`F1`)에서 **Dev Containers: Reopen in Container**를 실행합니다.
3. 처음에는 이미지(`mcr.microsoft.com/devcontainers/java:25-trixie`)와 feature(Node, Claude Code)를 받아 컨테이너를 만듭니다. 시간이 걸립니다.
4. 컨테이너 설정(`customizations.vscode.extensions`)에 따라 Java 확장 팩, Gradle, Spring Boot 확장이 설치됩니다.

### 앱 포트: 8081

`.devcontainer/devcontainer.json`에서 두 가지를 설정합니다.

- `containerEnv`의 `SERVER_PORT=8081`: Spring Boot가 이 환경 변수를 읽어 8081번 포트로 뜹니다. (`application.properties`에는 포트 설정이 없습니다.)
- `forwardPorts`의 `8081`: 컨테이너의 8081을 호스트로 포워딩합니다.

포트를 바꾸려면 위 두 값을 함께 고치고 컨테이너를 Rebuild하세요. 8081로 정한 이유는 저장소 파일에 적혀 있지 않습니다.

### Rebuild 뒤에도 남는 것

`mounts`가 컨테이너 파일시스템 밖의 Docker 볼륨을 붙이기 때문에, 컨테이너를 다시 만들어도 볼륨의 내용은 유지됩니다.

| 대상 | 컨테이너 경로 | 볼륨 |
| --- | --- | --- |
| Gradle 캐시 | `/home/vscode/.gradle` | `shop-gradle-home` |
| Claude Code 설정·로그인 | `/home/vscode/.claude` | `claude-code-config-${devcontainerId}` |

- `CLAUDE_CONFIG_DIR`이 `/home/vscode/.claude`로 지정되어 있어, Claude Code 설정이 이 볼륨에 저장됩니다. 그래서 Rebuild 후에도 로그인이 유지됩니다. 볼륨 이름의 `${devcontainerId}`는 dev container마다 정해지는 값입니다.
- `postCreateCommand`가 두 경로를 `vscode` 사용자 소유로 바꿉니다.
- 볼륨 자체를 지우면 캐시와 로그인도 사라집니다.

## 빌드와 실행

컨테이너 안의 터미널에서 프로젝트 루트의 Gradle wrapper를 사용합니다.

```bash
./gradlew build      # 빌드 (테스트 포함)
./gradlew test       # 테스트만
./gradlew bootRun    # 앱 실행
```

dev container 안에서 `bootRun`을 실행하면 `SERVER_PORT` 설정에 따라 http://localhost:8081 로 접속합니다.
