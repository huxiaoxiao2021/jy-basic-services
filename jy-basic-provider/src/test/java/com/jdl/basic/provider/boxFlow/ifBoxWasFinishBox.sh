
#ifBoxWasFinishBox 是否是成品包
curl -XPOST http://127.0.0.1:22000/com.jdl.basic.api.service.boxFlow.CollectBoxFlowDirectionVerifyJsfService/jy-basic-server-local/ifBoxesWasFinishBox   -H "token:jy-basic-server-token" -d '
[
{"endSiteId":[3],"flowType":2,"startSiteId":1,"transportType":1}
]
'