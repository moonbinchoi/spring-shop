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

> **git worktree를 열 때:** 이 저장소의 linked git worktree를 컨테이너에서 열면 VS Code 사용자 설정 `dev.containers.experimentalMountGitWorktreeCommonDir`를 켜야 컨테이너 안에서 git이 동작합니다. 이 저장소의 worktree는 `git worktree add --relative-paths`로 만들어 확인했습니다.

### 앱 포트: 8081

`.devcontainer/devcontainer.json`에서 두 가지를 설정합니다.

- `containerEnv`의 `SERVER_PORT=8081`: Spring Boot가 이 환경 변수를 읽어 8081번 포트로 뜹니다. (`application.properties`에는 포트 설정이 없습니다.)
- `forwardPorts`의 `8081`: 컨테이너의 8081을 호스트로 포워딩합니다.

포트를 바꾸려면 위 두 값을 함께 고치고 컨테이너를 Rebuild하세요. 8080 대신 8081을 쓰는 이유는, 이 PC에서 컨테이너로 띄우는 다른 프로젝트와 호스트 포트가 겹치지 않게 하기 위해서입니다.

### Rebuild 뒤에도 남는 것

`mounts`가 컨테이너 파일시스템 밖의 Docker 볼륨을 붙이기 때문에, 컨테이너를 다시 만들어도 볼륨의 내용은 유지됩니다.

| 대상 | 컨테이너 경로 | 볼륨 |
| --- | --- | --- |
| Gradle 캐시 | `/home/vscode/.gradle` | `shop-gradle-home` |
| Claude Code 설정·로그인 | `/home/vscode/.claude` | `claude-code-config-${devcontainerId}` |

- `CLAUDE_CONFIG_DIR`이 `/home/vscode/.claude`로 지정되어 있어, Claude Code 설정이 이 볼륨에 저장됩니다. 볼륨 이름의 `${devcontainerId}`는 Rebuild를 해도 바뀌지 않으므로 같은 이름의 볼륨이 다시 붙고, 그래서 로그인이 유지됩니다.
- `postCreateCommand`가 두 경로를 `vscode` 사용자 소유로 바꿉니다.
- 볼륨 자체를 지우면 캐시와 로그인도 사라집니다.

### Claude Code 첫 로그인

컨테이너 안의 터미널에서 `claude`를 처음 실행하면 로그인을 안내합니다. 브라우저에서 로그인한 뒤 콜백이 컨테이너에 닿지 않으면, 브라우저에 표시된 코드를 터미널의 `Paste code here if prompted`에 붙여 넣습니다. 로그인 정보는 위 표의 `claude-code-config-${devcontainerId}` 볼륨에 저장되므로 Rebuild 뒤에도 다시 묻지 않습니다.

### 줄 끝 규칙

`.gitattributes` 맨 위의 `* text=auto eol=lf`를 지우지 마세요. 컨테이너에는 사용자 `.gitconfig`만 복사되고 호스트 시스템 설정의 `core.autocrlf`는 따라오지 않습니다. 이 줄이 없으면 호스트에서 체크아웃한 CRLF 파일이 컨테이너에서만 modified로 보입니다.

## 빌드와 실행

컨테이너 안의 터미널에서 프로젝트 루트의 Gradle wrapper를 사용합니다.

```bash
./gradlew build      # 빌드 (테스트 포함)
./gradlew test       # 테스트만
./gradlew bootRun    # 앱 실행
```

dev container 안에서 `bootRun`을 실행하면 `SERVER_PORT` 설정에 따라 http://localhost:8081 로 접속합니다.

## 호스트에서 컨테이너 명령 실행

호스트 터미널에서 컨테이너 안의 명령을 실행할 때는 [devcontainer CLI](https://github.com/devcontainers/cli)를 씁니다. 호스트에 Node.js와 npm이 필요합니다.

```powershell
npm install -g @devcontainers/cli
devcontainer exec --workspace-folder <이 폴더의 호스트 경로> ./gradlew test
devcontainer exec --workspace-folder <이 폴더의 호스트 경로> --remote-env KEY=value <명령>
```

- 이미 떠 있는 dev container에서만 동작합니다. 먼저 VS Code에서 컨테이너로 열어 두세요. 이 폴더로 연 컨테이너가 없으면 `Dev container not found.` 오류로 멈추고, 컨테이너를 새로 만들지 않습니다.
- 컨테이너는 이름이 아니라 폴더 라벨로 찾으므로, Rebuild로 컨테이너 이름이 바뀌어도 같은 명령을 씁니다.
- `remoteUser`(`vscode`)로, 작업 공간 폴더(`/workspaces/<폴더 이름>`)에서 실행됩니다.
- `docker exec`를 옵션 없이 쓰면 `root`로 `/`에서 실행됩니다. `HOME`이 `/root`가 되어 복사된 `.gitconfig`를 읽지 못하고, root 소유 파일이 생깁니다. `docker exec`를 쓴다면 `-u vscode -w /workspaces/<폴더 이름>`을 함께 줍니다.
