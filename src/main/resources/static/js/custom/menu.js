function initTable() {
    $('#table').bootstrapTable({
        url: '/menu/menuList',
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
            {field: 'id', title: 'ID', visible: false},
            {field: 'index', title: '序号'},
            {field: 'name', title: '菜单名称'},
            {field: 'url', title: 'URL'}
        ]
    });

    $("#btn_add").click(function () {
        initMenus();
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

//menu init
function initMenus() {
    var menus = [
        {index: 1, name: "部门管理", url: "/dept"},
        {index: 2, name: "用户管理", url: "/user"},
        {index: 3, name: "角色管理", url: "/role"},
        {index: 4, name: "权限管理", url: "/permission"},
        {index: 5, name: "菜单管理", url: "/menu"},
        {index: 6, name: "操作管理", url: "/operation"}
    ];
    for (var ind = 0; ind < menus.length; ind++) {
        $.ajax({
            type: 'POST',
            url: '/menu',
            contentType: "application/json",
            data: JSON.stringify(menus[ind]),
            success: function (data, textStatus) {
                if (textStatus === "success")
                    toastr.info('新增成功！');
            }
        });
    }
    $("#table").bootstrapTable('refresh');
}

//menu del
function delMenu() {
    var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
    var url = "/menu/";
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