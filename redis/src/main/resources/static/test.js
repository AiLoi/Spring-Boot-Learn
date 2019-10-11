var url = vh_DDAttendance_ListVistor
    , pagename = "DDAttendance_List"
    , get = function (action, data, callback) { return MG.get.wcf(url, pagename, action, data, callback); }
    , post = function (action, data, callback) { return MG.post.wcf(url, pagename, action, data, callback); };
$(document).ready(function () {
    var defalutFilter = []
        , powers = {}
        , comboDataList = {}
        , checkObj = {
    };
    //数据来源
    comboDataList.SourceType = [{ id: "ATM", text: "考勤机", title: "数据来源" }, { id: "BEACON", text: "IBeacon" }, { id: "DING_ATM", text: "钉钉考勤机" }, { id: "USER", text: "用户打卡" }, { id: "BOSS", text: "老板改签" },
        { id: "APPROVE", text: "审批系统" }, { id: "SYSTEM", text: "考勤系统" }, { id: "AUTO_CHECK", text: "自动打卡" }];
    //考勤类型
    comboDataList.CheckType = [{ id: "OnDuty", text: "上班", title: "考勤类型" }, { id: "OffDuty", text: "下班" }];
    //打卡结果
    comboDataList.TimeResult = [{ id: "Normal", text: "正常", title: "打卡结果" }, { id: "Early", text: "早退" }, { id: "Late", text: "迟到" }, { id: "SeriousLate", text: "严重迟到" }, { id: "Absenteeism", text: "旷工迟到" }, { id: "NotSigned", text: "未打卡" }];
    //打卡位置
    comboDataList.LocationResult = [{ id: "Normal", text: "范围内", title: "打卡位置" }, { id: "Outside", text: "范围外" }, { id: "NotSigned", text: "未打卡" }];
    var dom_list_table = $("#entitylist")
        , dom_filter = $("#filterTag")
        , dom_btn_search = $("#searchBtn");
    var ui_dataView
        ,ui_searcher
        ,ui_filter = new MG.FilterManager(dom_filter, function (d) {
        buildList({filter:d.f, param:d.p,page:1});
    });
    function buildList(opts){
        var filter = opts.filter ;
        if(!filter)
            filter = [] ;
        if (!opts.nodefalut) {
            $.each(defalutFilter, function(i, _i) {
                if (_i.value) {
                    filter.push(_i);
                }
            });
        }
        ui_dataView.filters = filter;
        ui_dataView.params = opts.param;
        ui_dataView.pageSet.page = opts.page;
        if(opts.size)
            ui_dataView.pageSet.size = opts.size;
        if(opts.order)
            ui_dataView.pageSet.orders = opts.order;
        if(!opts.rebuild)
            ui_dataView.buildList();
        else
            ui_dataView.buildList(true);
    }

    function getFormValue(form){
        var updata = {};
        //为提交的实体赋值.只是示例.自己看着写
        $.each(form.find(":text"), function (i, _i) {
            var name = $(_i).attr("name").replace("form_", "");
            if ($(_i).is(".showuser") || $(_i).is(".showorg")) {
                updata[name] = $(_i).data("value");
            } else {
                updata[name] = $(_i).val();
            }
        });
        //为提交的实体赋值.只是示例.自己看着写
        $.each(form.find("select"), function (i, _i) {
            var val = $(_i).val();
            if(val + "" != "-1"){
                updata[$(_i).attr("name").replace("form_", "")] = val;
            }
        });
        return updata ;
    }
    function setFormValue(form,value){
        //为提交的实体赋值.只是示例.自己看着写
        $.each(form.find(":text"), function (i, _i) {
            var val = value ?value[$(_i).attr("name").replace("form_", "")]:"" ;
            if ($(_i).is(".showuser") || $(_i).is(".showorg")) {
                $(_i).data("value",val) ;
                $(_i).val(value ?value[$(_i).attr("name").replace("form_", "")+"name"]:"")  ;
            } else {
                $(_i).val(val)  ;
            }
        });
        //为提交的实体赋值.只是示例.自己看着写
        $.each(form.find("select"), function (i, _i) {
            var val = value ?value[$(_i).attr("name").replace("form_", "")]:-1 ;
            $(_i).val(val) ;
        });
    }
    //建立基础的字典选择等的Filter以及初始化数据
    function initDictAndFilter(data){
        comboDataList.ResultFlag = MG.ui.plugin.buildJsData(data.ResultFlagList);
        comboDataList.ResultFlag[0].title = "考勤结果";
        comboDataList.Corp = [];
        $.each(data.CorpList, function (i, _i) {
            comboDataList.Corp.push({ id: _i.DDCorpID, text: _i.Name });
        });
        //$.each(comboDataList, function (i, _i) {
        //    _i.splice(0, 0, { id: -1, text: "=请选择=" });
        //});
    }
    //初始化本页面的权限
    function initPagePower(data){
        powers.canadd = data.canadd;
        powers.canupdate = data.canupdate;

        if (MG.manager()) {
            MG.manager().append("这里可以换文字也可以什么都不写.");
        }
    }
    //初始化页面的UI的标签.(选人,选组织,下拉,日期)
    function initPageUIDom(){
        //选人,绑定值变更事件
        $.each($(".showuser"), function (i, _i) {
            MG.ui.plugin.OneUser(null, $(_i), function (user) {
                //user.UserId,user.UserName ;
            });
        });
        //选组织,绑定值变更事件
        $.each($(".showorg"), function (i, _i) {
            MG.ui.plugin.OneOrg(null,null, $(_i), function (org) {
                //org.OrgId,org.OrgName ;
            });
        });
        //下拉框字典
        $.each($(".showdict"), function (i, _i) {
            var name = $(_i).attr("name");
            var tmpname = name.replace("form_", "");
            var dom = $("<select class='select01'/>");
            $.each(comboDataList[tmpname], function (j, _j) {
                dom.append("<option value='" + _j.id + "'>" + _j.text + "</option>");
            });
            dom.attr("name", name);
            $(_i).replaceWith(dom);
        });
        //日期选择
        $.each($(".showdate"), function (i, _i) {
            MgTT.pickadate($(_i));
        });

        //绑定头部搜索条件
        for (var key in comboDataList) {
            $.each(comboDataList[key],function (i, e) {
                var title = comboDataList[key][0].title;
                var target = '<a href="javascript:void(0);" class="btn btn-small btn-success" data-id="${id}" data-type="${type}" data-title="${title}" onclick="addDom()">${text}</a>'
                    .replace("${id}", e.id).replace("${type}", key).replace("${title}", title).replace("${text}", e.text);
                $("#" + key).append(target);
            });
        }
    }


    //选择结果进行拼接
    function addDom(dom) {
        if (!dom) retuurn;
        var target = '<span class="namebox" data-id="${id}" data-type="${type}">${title}:${text}<a href="javascript:void(0);" onclick="$(this).parent().remove();" ></a></span>'
            .replace("${id}", $(dom).data("id")).replace("${type}", $(dom).data("type")).replace("${title}", $(dom).data("title")).replace("${text}", $(dom).text());
        var canAdd = true;
        $.each($(".namebox"), function (i, _i) {
            if (($(dom).data("id") == $(_i).data("id")) && ($(_i).data("type") == $(dom).data("type"))) {
                canAdd = false;
            }
        });
        if (canAdd) {
            $("#resultdiv").append(target);
        }
    }

    //初始化添加和编辑的弹出框
    function initDialog(dom,title,callback){
        MG.ui.plugin.initDialog(dom,title,function(){
            var updata = getFormValue(dom);
            var msg = MG.vali(updata,checkObj) ;
            if(msg && msg != ""){
                MG.ui.plugin.msgbox(msg.replace(/\n/g,"<br/>"));
                return;
            }
            (callback&&callback(updata));
        }) ;
    }


    function initList(data){
        //定义列表表头
        var titleData = [
            {
                text: "主键",
                width: 120,
                hidden: true,
                canhide: true,
                bind: "AttendanceId"
            },
            {
                text: "编号",
                width: 120,
                canhide: true,bind: "DDId"
            },
            //{
            //    text: "ddgroupid",
            //    width: 120,
            //    canhide: true,bind: "ddgroupid"
            //},
            //{
            //    text: "排班",
            //    width: 120,
            //    canhide: true,
            //    bind: "DDPlanId"
            //},
            //{
            //    text: "DDRecordId",
            //    width: 120,
            //    canhide: true,bind: "DDRecordId"
            //},
            {
                text: "工作日期",
                width: 120,
                formatter: function (v, d) {
                    return v != "" && v != undefined ? MgTT.tip_simple(v.MGFormatDate(), v) : "";
                },
                canhide: true,
                bind: "DDWorkDate"
            },
            {
                text: "用户",
                width: 120,
                canhide: true,
                formatter: function(v,d){
                    return MgTT.userTip({ id: v, text: d.Mapidname }).css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" });
                },
                bind: "Mapid"
            },
            {
                text: "考勤类型",
                width: 120,
                formatter: function (v, d) {
                    var ret = v; $.each(comboDataList['CheckType'], function (i, _i) { if (v == _i.id) { ret = _i.text; return; } });
                    return $("<div>" + ret + "</div>").css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" }).click(function () {
                        ui_filter.simpleAdd("DDCheckType", v, "考勤类型", ret);
                    });
                },
                canhide: true,
                bind: "DDCheckType"
            },
            {
                text: "打卡结果",
                width: 120,
                formatter: function (v, d) {
                    var ret = v; $.each(comboDataList['TimeResult'], function (i, _i) { if (v == _i.id) { ret = _i.text; return; } });
                    return $("<div>" + ret + "</div>").css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" }).click(function () {
                        ui_filter.simpleAdd("DDTimeResult", v, "打卡结果", ret);
                    });
                },
                canhide: true,
                bind: "DDTimeResult"
            },
            {
                text: "打卡位置",
                width: 120,
                formatter: function (v, d) {
                    var ret = v; $.each(comboDataList['LocationResult'], function (i, _i) { if (v == _i.id) { ret = _i.text; return; } });
                    return $("<div>" + ret + "</div>").css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" }).click(function () {
                        ui_filter.simpleAdd("DDLocationResult", v, "打卡位置", ret);
                    });
                },
                canhide: true,
                bind: "DDLocationResult"
            },
            //{
            //    text: "DDApproveId",
            //    width: 120,
            //    canhide: true,bind: "DDApproveId"
            //},
            //{
            //    text: "DDProcInstId",
            //    width: 120,
            //    canhide: true,bind: "DDProcInstId"
            //},
            {
                text: "迟到早退时间",
                width: 120,
                formatter: function (v, d) {
                    return v != "" && v != undefined ? MgTT.tip_simple(v.MGFormatDate(), v) : "";
                },
                canhide: true,
                bind: "DDBaseCheckTime"
            },
            {
                text: "实际打卡时间",
                width: 120,
                formatter: function (v, d) {
                    return v != "" && v != undefined ? MgTT.tip_simple(v.MGFormatDate(), v) : "";
                },
                canhide: true,
                bind: "DDUserCheckTime"
            },
            {
                text: "数据来源",
                width: 120,
                formatter: function (v, d) {
                    var ret = v; $.each(comboDataList['SourceType'], function (i, _i) { if (v == _i.id) { ret = _i.text; return; } });
                    return $("<div>" + ret + "</div>").css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" }).click(function () {
                        ui_filter.simpleAdd("DDSourceType", v, "数据来源", ret);
                    });
                },
                canhide: true,
                bind: "DDSourceType"
            },
            {
                text: "添加时间",
                width: 120,
                formatter: function (v, d) {
                    return v != "" && v != undefined ? MgTT.tip_simple(v.MGFormatDate(), v) : "";
                },
                canhide: true,
                bind: "AddTime"
            },
            //{
            //    text: "CorpAutoID",
            //    width: 120,
            //    canhide: true,bind: "CorpAutoID"
            //},
            {
                text: "钉钉机构",
                width: 120,
                formatter: function (v, d) {
                    var ret = v; $.each(comboDataList['Corp'], function (i, _i) { if (v == _i.id) { ret = _i.text; return; } });
                    if (ret) {
                        return $("<div>" + ret + "&nbsp;</div>").css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" }).append($('<a href="Corp_List.aspx?corpautoid=' + v + '">查看</a>').addClass("btn").css({ "padding": "3px 7px", "border": "solid 1px #7b8a97" }));
                    } else {
                        return "";
                    }
                },
                canhide: true,
                ind: "DDCorpId"
            },
            //{
            //    text: "Mapid",
            //    width: 120,
            //    canhide: true,bind: "Mapid"
            //},
            {
                text: "最后更新时间",
                width: 120,
                formatter: function (v, d) {
                    return v != "" && v != undefined ? MgTT.tip_simple(v.MGFormatDate(), v) : "";
                },
                canhide: true,
                bind: "LastTime"
            },
            {
                text: "考勤结果",
                width: 120,
                formatter: function (v, d) {
                    var ret = v; $.each(comboDataList['ResultFlag'], function (i, _i) { if (v == _i.id) { ret = _i.text; return; } });
                    return $("<div>" + ret + "</div>").css({ "color": "rgb(72, 127, 109)", "cursor": "pointer" }).click(function () {
                        ui_filter.simpleAdd("ResultFlag", v, "考勤结果", ret);
                    });
                },
                canhide: true,
                bind: "ResultFlag"
            }];
        var btns = [];

        //定义列表
        ui_dataView = new MG.tableView(dom_list_table, $(".ttpager"), titleData, pagename, url, "getlist",{
            btns: $.merge(btns, [{
                id: "btn_search",
                ico: "w2ui-icon-search",
                name: "查询",
                click: function () {
                    ui_searcher.data("search_show")();
                }
            }])
        }) ;
    }
    //初始化查询器
    function initSearcher(){
        ui_searcher = MgTT.tp_searcher({
            Columns: {
                //"DDPlanId": { name: "排班" },
                "Mapid": { name: "用户", type: "user", isduoxuan: true, filter: "in" },
                "DDCheckType": { name: "考勤类型", type: "combo", data: comboDataList['CheckType'] },
                "DDTimeResult": { name: "时间结果",  type: "combo", data: comboDataList['TimeResult'] },
                "DDLocationResult": { name: "位置结果", type: "combo", data: comboDataList['LocationResult'] },
                "DDSourceType": { name: "数据来源",  type: "combo", data: comboDataList['SourceType'] },
                "DDCorpId": { name: "钉钉机构", type: "combo", data: comboDataList['Corp'] },
                "ResultFlag": { name: "考勤结果", type: "combo", data: comboDataList['ResultFlag'] },
                "DDWorkDate": { name: "工作日期", type: "qdate" },
                //"DDUserCheckTime": { name: "实际打卡时间", type: "qdate" },
                //"DDBaseCheckTime": { name: "迟到早退", type: "qdate" },
                //"AddTime": { name: "添加时间", type: "qdate" },
                //"Mapid": { name: "Mapid"  },
                //"AttendanceId": { name: "AttendanceId" },
                //"DDId": { name: "DDId"  },
                //"DDGroupId": { name: "DDGroupId"  },
                //"DDRecordId": { name: "DDRecordId"  },
                //"DDApproveId": { name: "DDApproveId"  }
            },
            filtertag: ui_filter,
            callback: function (filter, poster) {
                buildList({filter:filter,param:poster,page:1}) ;
            }
        });
    }

    function init() {
        get("inithandler", {}, function(data) {
            initDictAndFilter(data) ;

            initPagePower(data) ;

            initPageUIDom();

            initSearcher();

            initList(data);

            buildList({rebuild:true}) ;
        });
    }


    //初始化开始
    init();


    //选择人
    $("#btnSelUser").click(function () {
        $("<div />").userselection({
            isduoxuan: 1,//是否多选, 可以为空, 默认 true                                                                      （功能类）
            ispinyin: 0, //是否开启拼音,可以为空, 默认false                                                                    （功能类）
            text: "输名称、拼音或员工编号,敲回车搜索",   //搜索框默认文字                                                                    （功能类）
            pagesize: 20,               //默认每页显示                                                                         （功能类）
            existResults: "22565",//用来接收已存在信息并显示到结果栏  （功能类）
            ispowerorg: false,          //是否只能选择可视组织                                                               （限制类）
            isShowDeleted: 0,  //是否显示离职员工                                                                         （限制类）
            setIsJJr: [],//是否限制经纪人等级                                                                                     （限制类）
            setMustOrgId: [],//打开时显示的组织员工，可以为空，默认为登录人所在组织（区以上组织无效）                         （限制类）
            setOrgId: 0,//打开时显示的组织员工，可以为空，默认为登录人所在组织（区以上组织无效）                             （快捷操作类）
            canSelectId: 0,      //是否开启传入Id搜员工功能搜索框
            maxSelectNumber: 5,
            call: function (data) {
                //alert("选择了" + data.length + "个那个");
                var strSel = [];
                $.each(data, function (i, item) {
                    strSel.push(item.UserId + "," + item.UserName);
                    console.log(item.UserId);
                    reload()
                });

                //alert(strSel.join(";"));
            }//这是一个回调函数
        });
    });
    //选择门店
    $("#btnSelOrgM").click(function () {
        $("<div />").orgselection({
            text: "输名称或拼音,敲回车搜索",//搜索框默认文字                                                                      （功能类）
            pagesize: 20,//默认每页显示                                                                                       （功能类）
            isduoxuan: 1,//是否多选, 可以为空, 默认 true                                                                     （功能类）
            existResults: "",//接收已存在信息并显示到结果栏，只传Id                     （功能类）
            ispinyin: 0,//是否开启拼音,可以为空, 默认false                                                                    （功能类）
            isendorg: 0,//是否只能选择没有下级的组织                                                                          （限制类）
            orglevel: [700, 800],//可视的组织级别                                                                        （限制类）
            ispowerorg: 0,//是否只能选择可视组织                                                                            （限制类）
            isShowDeleted: 1,//是否显示已删除组织                                                                        （限制类）
            setParentOrgId: [],//可传多个,显示该父级ID的下一级组织                                             （快捷操作类）
            setParentOrgIdandShowAllOrg: [],//显示该父级ID下所有组织                                   （快捷操作类）
            canSelectId: 0,
            call: function (data) {
                //alert("选择了" + data.length + "个那个");
                var strSel = [];
                $.each(data, function (i, item) {
                    strSel.push(item.OrgID + "," + item.OrgName);
                });
                //alert(strSel.join(";"));
            }//这是一个回调函数
        });
    });

    //重新加载
    function reload() {
        console.log(ui_filter.getFilter());
        ui_filter.callback();
    }



});
