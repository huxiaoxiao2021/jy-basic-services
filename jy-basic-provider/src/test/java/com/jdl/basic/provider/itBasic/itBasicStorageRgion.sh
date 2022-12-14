#ItBasicRegionApi IT基础区域数据
curl -XPOST http://127.0.0.1:22000/com.jdl.basic.api.service.itBasic.ItBasicIpRegionApi/jy-basic-server-dev/queryPageList   -H "token:jy-basic-server-token" -d '
[
{"pageSize": 2, "pageNumber": 1}
]
'