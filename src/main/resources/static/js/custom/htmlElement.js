function initTable() {
    $('#table').bootstrapTable({
        url: '/htmlElement/elementList',
        toolbar: '#toolbar',
        striped: true,                      //是否显示行间隔色
        cache: false,
        sortable: false,                    //是否启用排序
        sortOrder: "asc",                   //排序方式
        pageNumber: 1,                      //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100, 'all'], //可供选择的每页的行数（*）
        pagination: true,                   //是否显示分页（*）
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: false,                //设置 true 将禁止多选。
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                  //是否显示详细视图和列表视图的切换按钮
        locale: 'zh-CN',					//中文支持,
        columns: [
            {checkbox: true},
            {field: 'id', title: 'ID'},
            {field: 'name', title: '页面元素名称'},
            {field: 'code', title: '代码'},
            {field: 'menuId', title: '所属菜单'},
        ]
    });

    $("#btn_add").click(function () {
        $('#modal_add').modal('show');
    });

    $("#btn_del").click(function () {
        var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
        if (tobeDeleted.length === 0){
            toastr.warning("请选择要删除的数据！");
            return;
        }
        $('#modal_del').modal('show');
    });
}

function initTree() {
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
            },
        },
        view: {
            showLine: true,
            dblClickExpand: false,
        },
        callback: {
            onClick: onClick
        }
    };

    $.get("/menu/menuList",function (data) {
        $.each(data,function (index, row) {
            row.open = true;
            delete row['url'];
        });
        $.fn.zTree.init($("#menuTree"), setting, data);
        $("#BLANK").css("height",$("#menuTreeContent").height() + "px");
    });
}

function createHtmlElement() {
    var newElement = {
        name: $("#name").val(),
        code: $("#code").val(),
        menuId: $("#menuId").val()
    };

    $.ajax({
        type: 'POST',
        url: '/htmlElement',
        contentType: "application/json",
        data: JSON.stringify(newElement),
        success: function (data, textStatus) {
            if (textStatus === "success") {
                toastr.info('新增成功！');
                $("#table").bootstrapTable('refresh');
            }
        }
    });
}

function deleteHtmlElement() {
    var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
    var url = "/htmlElement/";
    $.each(tobeDeleted, function (index, row) {
        if (index === 0)
            url += row.id;
        url += "," + row.id;
    });

    $.ajax({
        type: "DELETE",
        url: url,
        success: function (data, textStatus) {
            if (textStatus === "success") {
                toastr.info("删除成功！");
                $("#table").bootstrapTable('refresh');
            }
        },
    });
}

// 显示菜单树控件
function showMenu(treeContentId) {
    var deptObj = $("#menu");
    var deptOffset = $("#menu").offset();
    $("#"+treeContentId).css({
        visibility: "visible",
        left: deptOffset.left + "px",
        top: deptOffset.top + deptObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $(".treeContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.class == "treeContent" || $(event.target).parents(".treeContent").length>0)) {
        hideMenu();
    }
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var nodes = zTree.getSelectedNodes();
    var node = nodes[nodes.length - 1];
    $("#menu").attr("value", node.name);
    $("#menuId").val(node.id);
}
