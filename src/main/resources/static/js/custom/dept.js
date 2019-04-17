var zNodes = [], zTree, rMenu;

function initTable() {
    $('#table').bootstrapTable({
        url: '/dept/deptList',
        toolbar: '#toolbar',
        striped: true,                      //是否显示行间隔色
        cache: false,
        sortable: false,                    //是否启用排序
        sortOrder: "asc",                   //排序方式
        pageNumber: 1,                      //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: [20, 50, 100, 'all'],     //可供选择的每页的行数（*）
        pagination: true,                   //是否显示分页（*）
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        clickToSelect: true,                //是否启用点击选中行
        singleSelect: true,                //设置 true 将禁止多选。
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                  //是否显示详细视图和列表视图的切换按钮
        locale: 'zh-CN',					//中文支持,
        columns: [
            {checkbox: true},
            {field: 'id', title: 'ID', visible: false},
            {field: 'name', title: '部门名称'},
            {field: 'shortName', title: '部门简称'},
            {field: 'parentId', title: '上级部门ID', visible: false},
            {field: 'parent', title: '上级部门'}
        ]
    });

    $("#btn_init").click(function () {
        var root = {name: "部门", shortName: "部门", index: 1};
        $.ajax({
            type: 'POST',
            url: '/dept',
            contentType: "application/json",
            data: JSON.stringify(root),
            success: function (data, textStatus) {
                if (textStatus === "success") {
                    toastr.info('新增成功！');
                    window.location.reload();
                }
            }
        });
    });

    $("#dept_add_submit").click(function () {
        var name = $("#name_add").val();
        var shortName = $("#shortName_add").val();
        var parentId = zTree.getSelectedNodes().length === 0 ? null : zTree.getSelectedNodes()[0].id;
        $.ajax({
            type: 'POST',
            url: '/dept',
            contentType: "application/json",
            data: JSON.stringify({
                name: name,
                shortName: shortName,
                parentId: parentId
            }),
            success: function (data, textStatus) {
                if (textStatus === "success") {
                    toastr.info('新增成功！');
                    $("#table").bootstrapTable('refresh');
                    var newNode = {id: data.id, name: name, shortName: shortName, parentId: parentId};
                    if (zTree.getSelectedNodes()[0]) {
                        newNode.checked = zTree.getSelectedNodes()[0].checked;
                        zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
                    } else {
                        zTree.addNodes(null, newNode);
                    }
                }

            }
        });
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
            dblClickExpand: true,
        },
        check: {
            enable: false,
        },
        callback: {
            onRightClick: onRightClick,
        },
    };

    $.get("/dept/deptList", function (data) {
        $.each(data, function (index, row) {
            row.open = true;
        });
        zNodes = data;
        $.fn.zTree.init($("#tree"), setting, zNodes);
        zTree = $.fn.zTree.getZTreeObj("tree");
        rMenu = $("#rMenu");
    });
}

//增加节点
function addTreeNode() {
    hideRMenu();
    $("#name_add").val(null);
    $("#shortName_add").val(null);

    if (zTree.getSelectedNodes().length === 0)
        $("#parentId_add").val(null);
    else
        $("#parentId_add").val(zTree.getSelectedNodes()[0].name);
    $('#modal_add').modal('show');
}

//删除节点
function removeTreeNode() {
    hideRMenu();
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length > 0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            if (confirm(msg) == true) {
                delDept(nodes[0]);
            }
        } else {
            delDept(nodes[0]);
        }
    }
}

function delDept(node) {
    var url = "/dept/" + node.id;
    $.ajax({
        type: "DELETE",
        url: url,
        success: function (data, textStatus) {
            if (textStatus === "success") {
                toastr.info("删除成功！");
                $("#table").bootstrapTable('refresh');
                zTree.removeNode(node);
            }
        },
    });
}

// 改写右键事件
function onRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        zTree.selectNode(treeNode);
        showRMenu("node", event.clientX, event.clientY);
    }
}

// 显示右键菜单
function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type == "root") {
        $("#m_del").hide();
        $("#m_check").hide();
        $("#m_unCheck").hide();
    } else {
        $("#m_del").show();
        $("#m_check").show();
        $("#m_unCheck").show();
    }

    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}

// 隐藏右键菜单
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}

// 改写鼠标落下事件
function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        rMenu.css({"visibility": "hidden"});
    }
}
