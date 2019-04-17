function initTable() {
    $('#table').bootstrapTable({
        url: '/user/userList',
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
        singleSelect: false,                 //设置 true 将禁止多选。
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                  //是否显示详细视图和列表视图的切换按钮
        locale: 'zh-CN',					//中文支持
        columns: [
            {checkbox: true},
            {field: 'id', title: 'ID', visible: false},
            {field: 'username', title: '用户名'},
            {field: 'chName', title: '中文名'},
            {field: 'telephone', title: '电话'},
            {field: 'descript', title: '描述'},
            {field: 'locked', title: '是否锁定'},
            {field: 'createTime', title: '创建时间'},
            {field: 'deptName', title: '所属部门'},
            {field: 'roles', title: '所屬角色'}
        ]
    });

    $("#btn_add").click(function () {
        $('#modal_add').modal('show');
    });

    $("#btn_del").click(function () {
        var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
        if (tobeDeleted.length === 0) {
            toastr.warning("请选择要删除的数据！");
            return;
        }
        $('#modal_del').modal('show');
    });
}

function loadTree() {
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

    $.get("/dept/deptList", function (data) {
        $.each(data, function (index, row) {
            row.open = true;
        });
        $.fn.zTree.init($("#deptTree"), setting, data);
        $("#BLANK").css("height", $("#deptTreeContent").height() + "px");
    });
}

// 新增用户，提交表单
function createUser() {
    var password = $("#password").val();
    var password_confirm = $("#password_confirm").val();
    if (password != password_confirm) {
        toastr.error("密码不一致！");
        return;
    }
    var departmentId = $("#departmentId").val();
    if (departmentId == null || departmentId === '') {
        toastr.error("请选择部门！");
        return;
    }
    var newUser = {
        username: $("#username").val(),
        password: password,
        chName: $("#chName").val(),
        telephone: $("#telephone").val(),
        descript: $("#descript").val(),
        departmentId: departmentId
    };

    $.ajax({
        type: 'POST',
        url: '/user',
        contentType: "application/json",
        data: JSON.stringify(newUser),
        success: function (data, textStatus) {
            if (textStatus === "success") {
                toastr.info('新增成功！');
                $("#table").bootstrapTable('refresh');
                cleanForm();
                loadTree();
            }
        }
    });
}

function cleanForm() {
    $("#username").val(null);
    $("#password").val(null);
    $("#password_confirm").val(null);
    $("#chName").val(null);
    $("#telephone").val(null);
    $("#descript").val(null);
    $("#department").val(null);
    $("#departmentId").val(null);
}

// 删除用户
function delUser() {
    var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
    var url = "/user/";

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
                loadTree();
            }
        },
    });
}

// 显示部门树控件
function showMenu(treeContentId) {
    var deptObj = $("#department");
    var deptOffset = $("#department").offset();
    $("#" + treeContentId).css({
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
    if (!(event.target.id == "menuBtn" || event.target.class == "treeContent" || $(event.target).parents(".treeContent").length > 0)) {
        hideMenu();
    }
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var nodes = zTree.getSelectedNodes();
    var node = nodes[nodes.length - 1];
    $("#department").attr("value", node.name);
    $("#departmentId").val(node.id);
}
