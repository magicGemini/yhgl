/**
 * Created by yangzy on 2018/9/5.
 */
var defaultSetting = {
    "sidebarWidth": "184px"
}
toastr.options.positionClass = 'toast-top-center';
toastr.options.showDuration = "300";
toastr.options.hideDuration = "1000";
toastr.options.timeOut = "1000";
toastr.options.extendedTimeOut = "1000";

// 侧边栏打开
function w3_open() {
    $(".glyphicon-menu-hamburger").css("display", "none");
    $("nav").css("marginLeft", defaultSetting.sidebarWidth);
    $("#pageContent").css("marginLeft", defaultSetting.sidebarWidth);

    var sideBar = $("#sidebar");
    sideBar.css("width", defaultSetting.sidebarWidth);
    sideBar.css("display", "block");
}

// 测边栏关闭
function w3_close() {
    $(".glyphicon-menu-hamburger").css("display", "block");
    $("nav").css("marginLeft", "0");
    $("#pageContent").css("marginLeft", "0");
    $("#sidebar").css("display", "none");
}

// 加载侧边栏
function loadMenuBar() {
    var tree = [
        {index: 1, text: "部门管理", href: "/dept.html"},
        {index: 2, text: "人员管理", href: "/user.html"},
        {index: 3, text: "角色管理", href: "/role.html"},
        {index: 4, text: "权限管理", href: "/permission.html"},
        {index: 5, text: "菜单管理", href: "/menu.html"},
        {index: 6, text: "操作管理", href: "/operation.html"}
    ];
    $("#menuBars").treeview({
        data: tree,
        color: "#FFFFFF",
        backColor: "#000000",
        borderColor: "#000000",
        onhoverColor: "#428bca",
        showBorder: false,
        enableLinks: true
    });

    // $.get("/menu/menuBars", function (data) {
    //     $("#menuBars").treeview({
    //         data: JSON.parse(data),
    //         color: "#FFFFFF",
    //         backColor: "#000000",
    //         borderColor: "#000000",
    //         onhoverColor: "#428bca",
    //         showBorder: false,
    //         enableLinks: true,
    //     });
    // });

}
