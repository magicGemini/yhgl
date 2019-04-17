function initTable() {
    $('#table').bootstrapTable({
        url: '/permission/permissionList',
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
        singleSelect: true,                 //设置 true 将禁止多选。
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                  //是否显示详细视图和列表视图的切换按钮
        locale: 'zh-CN',					//中文支持,
        columns: [
            {checkbox: true},
            {field: 'id', title: 'ID', visible: false},
            {field: 'permission', title: '权限名称'},
            {field: 'type', title: '权限类型'},
            {field: 'descript', title: '描述'},
            {field: 'permissionContent', title: '权限内容'},
            {field: 'role', title: '所属角色'}
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

    $("#btn_edit").click(function () {
        var tobePermissionTo = $("#table").bootstrapTable('getSelections');
        if (tobePermissionTo.length != 1) {
            toastr.warning("请选择一条要分配的权限！");
            return;
        }
        $("#permission_edit").val(tobePermissionTo[0].permission);
        $("#descript_edit").val(tobePermissionTo[0].descript);
        $("#permissionType_edit").val(tobePermissionTo[0].type);
        $('#modal_edit').modal('show');
    });
}


/**
 * 加载树控件
 * ***/
function loadTree() {
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        },
        view: {
            showLine: true,
            dblClickExpand: false
        },
        callback: {
            onClick: onClick
        }
    };

    // 菜单树
    var blank_height = 0;
    $.get("/menu/menuList", function (data) {
        $.each(data, function (index, row) {
            row.open = true;
            delete row['url'];
        });
        $.fn.zTree.init($("#menuTree"), setting, data);
        if ($("#menuTreeContent").height() > blank_height) {
            blank_height = $("#menuTreeContent").height();
            $("#BLANK").css("height", blank_height + "px");
        }
    });
    // 操作树
    $.get("/operation/operationTree", function (data) {
        data = JSON.parse(data);
        $.each(data, function (index, row) {
            row.open = true;
        });
        $.fn.zTree.init($("#optTree"), setting, data);
        if ($("#optTreeContent").height() > blank_height) {
            blank_height = $("#optTreeContent").height();
            $("#BLANK").css("height", blank_height + "px");
        }
    });

    var setting2 = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        },
        view: {
            showLine: true,
            dblClickExpand: false
        },
        callback: {
            onClick: onClick2
        }
    };
    // 角色树
    $.get("/role/roleTree", function (data) {
        data = JSON.parse(data);
        $.each(data, function (index, row) {
            row.open = true;
        });
        $.fn.zTree.init($("#roleTree"), setting2, data);
        $("#BLANK_2").css("height", $("#roleTreeContent").height() + "px");
    });

    $("#permissionType").change(function () {
        var selected = $(this).children('option:selected').val();
        if (selected === "menu") {
            $("#permissionType_label").val("菜单");
            $("#menuBtn").attr("onclick", "showMenu('menuTreeContent')");
        } else if (selected === "operation") {
            $("#permissionType_label").val("操作");
            $("#menuBtn").attr("onclick", "showMenu('optTreeContent')");
        }
    });
}

// create permission
function createPermission() {
    var newPermission = {
        permission: $("#permission").val(),
        descript: $("#descript").val(),
        permissionType: $("#permissionType").val(),
        permissionContent: $("#permissionGrpId").val()
    };

    $.ajax({
        type: 'POST',
        url: '/permission',
        contentType: "application/json",
        data: JSON.stringify(newPermission),
        success: function (data, textStatus) {
            if (textStatus === "success") {
                toastr.info('新增成功！');
                $("#table").bootstrapTable('refresh');
                cleanForm();
            }
        }
    });
}

// delete permission
function deletePermission() {
    var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
    var url = "/permission/";
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
        }
    });
}

function permissionToRole() {
    var permissionId = $("#table").bootstrapTable('getAllSelections')[0].id;
    var roleId = $("#permissionToId").val();
    $.ajax({
        type: "POST",
        url: "/permission/correlateRole",
        contentType: "application/json",
        data: JSON.stringify({
            permissionId: permissionId,
            roleId: roleId
        }),
        success: function (data, textStatus) {
            if (textStatus === "success") {
                toastr.info('分配权限成功！');
                $("#table").bootstrapTable('refresh');
            }
        }
    });
}

function cleanForm() {
    $("input[type=reset]").trigger("click");
}

// 显示树控件
function showMenu(treeContentId) {
    var deptObj = $("#permissionGrp");
    var deptOffset = $("#permissionGrp").offset();
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
    if (!(event.target.id.startsWith("menuBtn") || event.target.class == "treeContent" || $(event.target).parents(".treeContent").length > 0)) {
        hideMenu();
    }
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree.getSelectedNodes(), v = "";
    nodes.sort(function compare(a, b) {
        return a.id - b.id;
    });
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);

    var permissionGrp = $("#permissionGrp");
    permissionGrp.attr("value", v);
    var permissionGrpIds = [];
    $.each(nodes, function (index, row) {
        permissionGrpIds.push(row.id);
    });
    $("#permissionGrpId").val(permissionGrpIds.join(","));
}

// 显示角色树控件
function showRoleMenu() {
    var deptObj = $("#permissionTo");
    var deptOffset = $("#permissionTo").offset();
    $("#roleTreeContent").css({
        visibility: "visible",
        left: deptOffset.left + "px",
        top: deptOffset.top + deptObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function onClick2(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var nodes = zTree.getSelectedNodes();
    var node = nodes[nodes.length - 1];
    $("#permissionTo").attr("value", node.name);
    $("#permissionToId").val(node.id);
}