#ItBasicRegionApi IT基础区域数据
curl -XPOST http://127.0.0.1:22002/com.jdl.basic.api.service.transferDp.ConfigTransferDpApi/jy-basic-server-dev/queryPageList   -H "token:jy-basic-server-token" -d '
[
{"pageSize": 2, "pageNumber": 1}
]
'

#ItBasicRegionApi IT基础区域数据
curl -XPOST http://127.0.0.1:22000/com.jdl.basic.api.service.transferDp.ConfigTransferDpApi/jy-basic-server-dev/logicDeleteById   -H "token:jy-basic-server-token" -d '
[
{"id":13,"updateUser":"wuyoude","updateUserName":"吴有德"}
]
'

# 箱号类型
curl -XPOST http://127.0.0.1:22000/com.jdl.basic.api.service.boxLimit.BoxlimitConfigJsfService/jy-basic-server-dev/getBoxTypeList   -H "token:jy-basic-server-token" -d '
[]
'