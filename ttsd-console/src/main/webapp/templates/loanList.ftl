<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>所有借款</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- link bootstrap css and js -->
    <link href="../style/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../style/libs/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="../js/libs/jquery-1.10.1.min.js"></script> <!-- jquery -->
    <script src="../js/libs/bootstrap.min.js"></script>
    <!-- link bootstrap css and js -->

    <link rel="stylesheet" href="../style/index.css">
</head>
<body>
<!-- header begin -->
<header class="navbar navbar-static-top bs-docs-nav" id="top" role="banner">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-navbar" aria-controls="bs-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="../../" class="navbar-brand"><img src="../images/logo.jpg" alt=""></a>
        </div>
    </div>
    <nav id="bs-navbar" class="collapse navbar-collapse">
        <div class="container-fluid">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="">系统主页</a>
                </li>
                <li>
                    <a href="" >项目管理</a>
                </li>
                <li>
                    <a href="../html/userManage/referees.html">用户管理</a>
                </li>
                <li>
                    <a href="" >财务管理</a>
                </li>
                <li>
                    <a href="" >文章管理</a>
                </li>
                <li>
                    <a href="" >安全管理</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<!-- header end -->

<!-- main begin -->
<div class="main">
    <div class="container-fluid">
        <div class="row">
            <!-- menu sidebar -->
            <div class="col-md-2">
                <ul class="nav bs-docs-sidenav">
                    <li class="active"><a href="index.html">所有借款</a></li>
                    <li><a href="projectManage/firstTrial.html">初审的借款</a></li>
                    <li><a href="projectManage/moneyCollect.html">筹款中借款</a></li>
                    <li><a href="projectManage/twoTrial.html">复审借款</a></li>
                    <li><a href="projectManage/Drain.html">还款中的借款</a></li>
                    <li><a href="projectManage/finishRefund.html">完成还款的借款</a></li>
                    <li> <a href="projectManage/check.html">已流标等借款</a></li>
                    <li><a href="projectManage/overdue.html">逾期借款</a></li>
                    <li><a href="projectManage/star.html">发起借款</a></li>
                </ul>
            </div>
            <!-- menu sidebar end -->

            <!-- content area begin -->
            <div class="col-md-10">
                <form action="" class="form-inline query-build">
                    <div class="form-group">
                        <label for="number">编号</label>
                        <input type="text" class="form-control" id="" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="number">项目名称</label>
                        <input type="text" class="form-control" id="" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="number">日期</label>

                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control"/>
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                        </div>
                        -
                        <div class='input-group date' id='datetimepicker2'>
                            <input type='text' class="form-control"/>
					                <span class="input-group-addon">
					                    <span class="glyphicon glyphicon-calendar"></span>
					                </span>
                        </div>
                    </div>

                    <!--<div class="form-group">-->
                    <!--<label for="">角色</label>-->
                    <!--<select class="form-control">-->
                    <!--<option>全部</option>-->
                    <!--<option>Ketchup</option>-->
                    <!--<option>Relish</option>-->
                    <!--</select>-->
                    <!--</div>-->

                    <button type="button" class="btn btn-sm btn-primary">查询</button>
                </form>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>项目名称</th>
                            <th>项目类型</th>
                            <th>标的类型</th>
                            <th>借款人</th>
                            <th>借款金额</th>
                            <th>借款期限</th>
                            <th>年化/活动(利率)</th>
                            <th>项目状态</th>
                            <th>发起时间</th>
                            <th>投资/还款记录</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>20150714000141</td>
                            <td>编辑</td>
                        </tr>
                        </tbody>

                    </table>
                </div>

                <!-- pagination  -->
                <nav>
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- pagination -->
            </div>
            <!-- content area end -->
        </div>
    </div>
</div>
<!-- main end -->

</body>
</html>