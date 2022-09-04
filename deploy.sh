mvn clean package
tag="$(date +%Y%m%d%H%M%S)"
#tag=latest
repo="ucas"
docker build -t ccr.ccs.tencentyun.com/mryao/"$repo":"$tag" .
docker push ccr.ccs.tencentyun.com/mryao/"$repo":"$tag"
