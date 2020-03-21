<%@ page session="true"%> <%@ page isELIgnored="false"%>
<body>
<!-- ------- LOGIN ------- -->
<div class="modal fade" id="appModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            </div>
            <div class="modal-body">
             <!-- Form Title -->
                    <div class="form-heading text-center">
                        <div class="title">Sign In</div>
                    </div>
                <form name="login" action="user/login" method="post" id="myform">
                    <span id="errorSpan" style="color: red; visibility: hidden;" ></span>
                    <div class="row">
                        <div class="col-md-12">
                            <input type="text" name="username" placeholder="Username" required /> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <input type="password" name="password" placeholder="Password" required /> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <input type="checkbox">Remember Me</input>
                        </div>
                        <div class="col-md-5 col-md-offset-1">
                            <label><a href="#" data-toggle="modal" data-target="#ForgotModal" data-dismiss="modal">Forget Password?</a></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-center">
                            <input type="submit" class="btn btn-md btn-danger" value="Login" ></button>
                        </div>
                    </div>
                    <!-- Social Line -->
                    <div class="social-line"> 
                        <a href="#"><i class="fa fa-facebook"></i></a> 
                        <a href="#"><i class="fa fa-google-plus"></i></a> 
                        <a href="#"><i class="fa fa-twitter"></i></a> 
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- ------- LOGIN Ends ------- -->
<script src="${pageContext.request.contextPath}/views/js/app.js" type="text/javascript"></script>
</body>
