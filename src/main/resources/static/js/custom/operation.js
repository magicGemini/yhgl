function initTable() {
    $('#table').bootstrapTable({
        url: '/operation/operationList',
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
            {field: 'id', title: 'ID',visible:false},
            {field: 'index', title: '序号'},
            {field: 'name', title: '操作名称'},
            {field: 'method', title: '操作'},
            {field: 'url', title: 'Url'}
        ]
    });

    $("#btn_add").click(function () {
        createOperation();
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

function createOperation() {
    var operations = [
        {index: 1, name: "新增", method: "ADD", url: "/dept"},
        {index: 2, name: "修改", method: "UPDATE", url: "/dept"},
        {index: 3, name: "查询", method: "QUERY", url: "/dept"},
        {index: 4, name: "删除", method: "DELETE", url: "/dept"},
        {index: 5, name: "新增", method: "ADD", url: "/user"},
        {index: 6, name: "修改", method: "UPDATE", url: "/user"},
        {index: 7, name: "查询", method: "QUERY", url: "/user"},
        {index: 8, name: "删除", method: "DELETE", url: "/user"},
        {index: 9, name: "新增", method: "ADD", url: "/role"},
        {index: 10, name: "修改", method: "UPDATE", url: "/role"},
        {index: 11, name: "查询", method: "QUERY", url: "/role"},
        {index: 12, name: "删除", method: "DELETE", url: "/role"},
        {index: 13, name: "新增", method: "ADD", url: "/permission"},
        {index: 14, name: "修改", method: "UPDATE", url: "/permission"},
        {index: 15, name: "查询", method: "QUERY", url: "/permission"},
        {index: 16, name: "删除", method: "DELETE", url: "/permission"},
        {index: 17, name: "新增", method: "ADD", url: "/menu"},
        {index: 18, name: "修改", method: "UPDATE", url: "/menu"},
        {index: 19, name: "查询", method: "QUERY", url: "/menu"},
        {index: 20, name: "删除", method: "DELETE", url: "/menu"},
        {index: 21, name: "新增", method: "ADD", url: "/operation"},
        {index: 22, name: "修改", method: "UPDATE", url: "/operation"},
        {index: 23, name: "查询", method: "QUERY", url: "/operation"},
        {index: 24, name: "删除", method: "DELETE", url: "/operation"}
    ];

    for (var ind = 0; ind < operations.length; ind++) {
        $.ajax({
            type: 'POST',
            url: '/operation',
            contentType: "application/json",
            data: JSON.stringify(operations[ind]),
            success: function (data, textStatus) {
                if (textStatus === "success")
                    toastr.info('新增成功！');
            }
        });
    }
    $("#table").bootstrapTable('refresh');

}

function deleteOperation() {
    var tobeDeleted = $("#table").bootstrapTable('getAllSelections');
    var url = "/operation/";
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