
# JAR 파일 경로 설정 (특정폴더와 파일명을 수정하세요)
JAR_PATH="$HOME/SC/sc.jar"

# 파일 존재 여부 확인 후 실행
if [ -f "$JAR_PATH" ]; then
    echo "Running $JAR_PATH..."
    java -jar "$JAR_PATH"
else
    echo "Error: 파일을 찾을 수 없습니다: $JAR_PATH"
fi