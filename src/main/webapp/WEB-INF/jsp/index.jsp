<!DOCTYPE html>
<html lang="en" dir="ltr">
	<head>
		<title>ProjectBook</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" href="css/index.css">
		<link rel="stylesheet" href="css/print.css" media="print">
		<link rel="stylesheet" href="css/jquery-ui-1.10.3.custom.css">
		
		<script src="js/lib/jquery-1.9.1.js"></script>
		<script src="js/lib/jquery-ui.js"></script>
        <script src="js/lib/jquery.form.js"></script>
        <script src="js/lib/jquery.validate.js"></script>
        <script src="js/index.js"></script>

	</head>
	<body>
		<header class="clearFix">
			<div class="wrap"> <a id="logo" href="#">ProjectBook</a>
				<hr>
			</div>
		</header>
		<hr>
		<div id="intro">
			<div class="inner">
				<div class="wrap clearFix">
					<h1>Manage your time.<strong>Anywhere.</strong></h1>
					<p id="slogan">Sigh in to boost your productivity!</p>
					<a id="sighInButton" class="button signInButton">SIGN IN</a> 
				</div>
			</div>
		</div>
		<hr>
		<div id="content">
			<div class="wrap clearFix">
				<h2>SOME OF THE COOL FEATURES</h2>
				<div class="clearFix">
					<div class="col floatLeft"> <img src="img/icon-awards.png" alt="" class="icon">
						<h3>Manage your projects</h3>
						<p>  A project is a temporary endeavor designed to produce a unique product, service or result with
						a defined beginning and end. Our app will help you to cope with all integral parts of project management: 
						discipline of planning, organizing, motivating, and controlling resources to achieve specific goals.</p>
					</div>
					<div class="col floatRight"> <img src="img/icon-backup.png" alt="" class="icon">
						<h3>Create your timetable</h3>
						<p>	All your acts of planning will be stored carefully to remind you about important events in your life
						to help to increase your effectiveness, efficiency and productivity.</p>
					</div>
				</div>
				<div class="clearFix">
					<div class="col floatLeft"><img src="img/icon-stats.png" alt="" class="icon">
						<h3>Assign and receive tasks</h3>
						<p>As you know task management can help both individuals and groups to achieve results, collaborate 
						and share knowledge for the accomplishment of collective goals. ProjectBook helps you to manage your tasks,
						and tasks of your inferiors in most rational way.</p>
					</div>
						<div class="col floatRight"><img src="img/icon-twitter.png" alt="" class="icon">
						<h3>Place your announcements on the main corporate board</h3>
						<p>Need to invite your colleagues to corporate party? Want to deliver your co-workers some important information?
						With ProjectBook's corporate board it's never been easier: add your announcements and attach files to them.</p>
					</div>
				</div>
				<div class="clearFix">
					<div class="col floatLeft"><img src="img/icon-location.png" alt="" class="icon">
						<h3>Access to your company information and files</h3>
						<p>All important files and information about your company always within a hand's reach.</p>
					</div>
					<div class="col floatRight"><img src="img/icon-tools.png" alt="" class="icon">
						<h3>Access to full registry of your colleagues contact data</h3>
						<p>Need to make phone call to your co-worker? ProjectBook's journal will help you to do that fast
                            and with no problem!
						All contact information about your colleagues in one place and easy to find.</p>
					</div>
				</div>
				<h2>WHAT OUR CUSTOMERS ARE SAYING</h2>
				<ul class="cols clearFix">
				<li>
					<p> Nice app! It's easy to use and well-designed. Helps me to work most efficient and productive way</p>
					<p><strong>Mika Moos</strong>, LA</p>
				</li>
				<li class="middle">
					<p>Just use it and follow your results. I bet it will exceed all your expectations!
                        Helps to organize yourself, prioritize your goals and stay focused on the main tasks.</p>
					<p><strong>Sven Ottlieb</strong>, Berlin</p>
				</li>
				<li>
					<p>Extremely user-friendly and well-designed app. Easy to make schedule and be in time everywhere!</p>
					<p><strong>Thomas Sommer</strong>, Vancouver</p>
				</li>
				</ul>
			</div>
		</div>
		<hr class="noPrint">
			<div id="twitter">
			  <div class="wrap clearFix"> <span class="icon"></span>
				<p>We're open for communication! Please follow us on Twitter <a href="#">https://twitter.com/</a></p>
			  </div>
			</div>
		<hr>
		<footer class="clearFix">
		  <div class="wrap clearFix">
			<p class="floatRight"> Copyright &copy; 2013-2014 <a href="#">IRYNA</a> &ndash; All Rights Reserved<br>
				 <p class="socialIcons"> <a href="#" class="rss">RSS</a> <a href="#" class="facebook">Facebook</a> <a href="#" class="twitter">Twitter</a> </p>
		  </div>
		</footer>
		
		<div id="dialog-form" class="dialog" style="padding:0">
            <div class="closeButton">
            </div>

			<div class="inner">
				<div id="login-box">
                    <form id="loginForm" method="POST">

                        <div id="serverError" >
                        </div>

                        <H2>Authorization</H2>
                        Welcome to ProjectBook!
                        <br />
                        <br />

                        <fieldset>
                            <div class="fieldgroup">
                                <div class="headline login" >
                                    <label for="uLogin">
                                        Email:
                                    </label>
                                </div>
                                <div class="fields">
                                    <input name="userLogin" id="uLogin" class="inputDLP"/>
                                </div>
                            </div>

                            <div class="fieldgroup">
                                <div class="headline password" >
                                    <label for="uPassword">
                                        Password:
                                    </label>
                                </div>
                                <div class="fields">
                                    <input name="userPassword" id="uPassword" class="inputDLP" type="password" />
                                </div>
                            </div>
                            <br>
                            <button id="loginButton" class="button loginButton">LOGIN</button>
                        </fieldset>
                    </form>
				</div>
			</div>
		</div>			
	</body>
</html>
