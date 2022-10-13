#verifyBoxFlowDirectionConfs 流向校验
curl -XPOST http://127.0.0.1:22000/com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionVerifyJsfService/jy-basic-server-local/verifyBoxFlowDirectionConfs   -H "token:jy-basic-server-token" -d '
[
{"boxReceiveId":3,"endSiteId":null,"flowType":2,"startSiteId":1,"transportType":1}
]
'