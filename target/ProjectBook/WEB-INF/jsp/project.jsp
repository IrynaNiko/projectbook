<!DOCTYPE html>
<html lang="en" dir="ltr">
	<head>
        <title>Projects</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="css/jquery-ui-1.10.3.custom.css">
		<link rel="stylesheet" href="css/project.css">
		<link rel="stylesheet" href="css/print.css" media="print">
		<link rel="stylesheet" href="css/ui.jqgrid.css" />
		<link rel="stylesheet" href="css/jquery.datetimepicker.css">
		<link rel="stylesheet" href="css/fullcalendar.css" />

        <script src="js/lib/jquery-1.9.1.js"></script>
        <script src="js/lib/jquery-ui.js"></script>

        <script src="js/i18n/grid.locale-en.js"></script>
        <script src="js/lib/jquery.jqGrid.js"></script>
        <script src="js/lib/jquery.validate.js"></script>

        <script src="js/lib/jquery.datetimepicker.js"></script>

        <script src="js/lib/moment.min.js"></script>
        <script src="js/lib/fullcalendar.js"></script>
        <script src="js/lib/mustache.js"></script>
        <script src="js/lib/jquery_dataTables.js"></script>

        <script src="js/project.js"></script>

        <script>
            $(function() {
                // HIDE / SHOW BLOСKS FUNCTIONS
                hideAndGreyAllButtons();
                $("#diaryIntro").show();
                $(".diary-container").show();
                $("#diaryPageBtn a").css( "color", "#FFFFFF");

                $("#projectPageBtn").click(function() {
                    hideAndGreyAllButtons();
                    $('#projectPageBtn a').css( "color", "white");
                    $("#projectIntro").show();
                    $(".project-container").show();
                });
                $("#taskPageBtn").click(function() {
                    hideAndGreyAllButtons();
                    $('#taskPageBtn a').css( "color", "white");
                    $("#taskIntro").show();
                    $(".task-container").show();
                });
                $("#diaryPageBtn").click(function() {
                    hideAndGreyAllButtons();
                    $('#diaryPageBtn a').css( "color", "white");
                    $("#diaryIntro").show();
                    $(".diary-container").show();
                });
                $("#announcementsPageBtn").click(function() {
                    hideAndGreyAllButtons();
                    $('#announcementsPageBtn a').css( "color", "white");
                    $("#announcementsIntro").show();
                    $(".announcements-container").show();
                });
                $("#colleaguesPageBtn").click(function() {
                    hideAndGreyAllButtons();
                    $('#colleaguesPageBtn a').css( "color", "white");
                    $("#colleaguesIntro").show();
                    $(".colleagues-container").show();
                });
                $("#companyInfoPageBtn").click(function() {
                    hideAndGreyAllButtons();
                    greyAllButtons();
                    $( '#companyInfoPageBtn a' ).css( "color", "white");
                    $("#companyInfoIntro").show();
                    $(".companyInfo-container").show();
                });
                function hideAndGreyAllButtons() {
                    hideAllConteiners();
                    hideAllHeaders();
                    greyAllButtons();
                };
                function hideAllConteiners() {
                    $(".project-container, .task-container, .diary-container").hide();
                    $(".announcements-container, .colleagues-container, .companyInfo-container").hide();
                };
                function hideAllHeaders() {
                    $("#projectIntro, #taskIntro, #diaryIntro").hide();
                    $("#announcementsIntro, #colleaguesIntro, #companyInfoIntro").hide();
                };
                function greyAllButtons() {
                    $( '#projectPageBtn a, #taskPageBtn a, #diaryPageBtn a' ).css( "color", "#a2a2a2;");
                    $( '#announcementsPageBtn a, #colleaguesPageBtn a, #companyInfoPageBtn a' ).css( "color", "#a2a2a2;");
                };
            });
        </script>
	</head>
	<body>
		<header class="clearFix">
			<div class="wrap"> <a id="logo">ProjectBook</a>
				<hr>
				<nav>
					<div id="nav"> <strong>Navigation</strong>
						<ul>
                            <li id="diaryPageBtn"> <a href="#">Scheduler</a></li>
							<li id="projectPageBtn"> <a href="#">Projects</a> </li>
							<li id="taskPageBtn"> <a href="#">Tasks</a> </li>
							<li id="announcementsPageBtn"> <a href="#">Notice board</a> </li>
							<li id="colleaguesPageBtn"> <a href="#">Colleagues</a> </li>
							<li id="companyInfoPageBtn"> <a href="#">Company files</a> </li>
						</ul>
					</div>
				</nav>
			</div>
		</header>
		<hr>
		<div class="intro">
			<div class="inner">
				<div class="clearFix">
					<div class="wrap">
						<h2>
                            <div class="userboard">Welcome, ${userName} ${userSurname}!
							    <a href="j_spring_security_logout" id="logOutPageBtn">| Log out  </a>
							</div>
						</h2>					
						<div id="projectIntro">
								<a id="addProjectButton" class="headerBt buttonPB">New</a> 
								<a id="editProjectButton" class="headerBt buttonPB">Edit</a> 
								<a id="deleteProjectButton" class="headerBt buttonPB">Delete</a> 
						</div>
						<div id="taskIntro">
								<a id="addTaskButton" class="headerBt buttonPB">New</a>
								<a id="editTaskButton" class="headerBt buttonPB">Edit</a>
								<a id="delTaskButton" class="headerBt buttonPB">Delete</a> 
						</div>
						<div id="diaryIntro">
								<a id="newEntryDiaryButton" class="headerBt buttonPB">New entry</a>
						</div>						
						<div id="announcementsIntro">
								<a id="newAnnButton" class="headerBt buttonPB">New</a>  
								<a id="editAnnButton" class="headerBt buttonPB">Edit</a> 
								<a id="deleteAnnButton" class="headerBt buttonPB">Delete</a> 
						</div>
						<div id="colleaguesIntro">
								<a id="newUserButton" class="headerBt buttonPB">New user</a>
						</div>					
						<div id="companyInfoIntro">
								<a id="newSectionButton" class="headerBt buttonPB">New section</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<hr>
		<div class="content">
			<div class="clearFix">
				<div class="wrap">
					<!--PROJECT CONTENT-->
					<div class="project-container">
						<table id="projectsTable"></table>
						<div id="pagingDivProjects"></div>
					</div>
					<!--TASK CONTENT-->
					<div class="task-container">
						<table id="tasksTable"></table>
						<div id="pagingDivTasks"></div>
					</div>
					<!--ANNOUNCEMENT CONTENT-->
					<div class="announcements-container">
						<div class="announcements-content">
                            <div class="annSection clearFix" id ="annList">

                            </div>
							<div class="buttonCentered">
                                <a id="loadMoreButton" class="buttonCenter iconRight">LOAD MORE <i class="more"></i></a>
                            </div>
						</div>
					</div>
					<!--DIARY CONTENT-->
					<div class="diary-container">
						<div id="pagingDivDiary">						
							<div id='calendar'>
							
							</div>						
						</div>
					</div>	
					<!--COLLEAGUES CONTENT-->
					<div class="colleagues-container">
                        <div class="colleagues-content">
                            <div class="usersSection clearFix" id ="userList">

                            </div>
                        </div>
					</div>
					<!--COMPANY INFO CONTENT-->
					<div class="companyInfo-container">
                        <div class="companyInfo-content">
                            <div class="clearFix" >
                                <ol class="fileSection" id ="sectionList">

                                </ol>
                            </div>
                        </div>
					</div>	
				</div>
			</div>
		</div>
		<footer class="clearFix">
			<div class="wrap">
				<p class="floatRight"> Copyright &copy; 2013-2015
                    <a href="#">IRYNA</a> &ndash; All Rights Reserved<br>
				<p class="socialIcons">
                    <a href="#" class="rss">RSS</a>
                    <a href="#" class="facebook">Facebook</a>
                    <a href="#" class="twitter">Twitter</a>
                </p>
			</div>
		</footer>
				
		<!--PROJECT DIALOG FORM  -->
		<div id="dialog-form-project" class="dialog" style="padding: 0px">
			<div class="closeButton">
            </div>
			<div class="inner">
				<div class="project-box boxes">
					<form id="projectForm">
						<div class="fieldgroup">
                            <div id="projectDateCreated" class="inputDLP EP"></div>
							<div class="headline" >
								Title:
							</div>
							<div class="fields">
								<input name="projectTitle" id="pTitle" class="inputDLP NP title" 
									title="Enter title of project here" maxlength="75" />
							</div>
						</div>

						<div class="fieldgroup description">
							<div class="headline" >
								Description:
							</div>
							<div class="fields description-field">
								<textarea name="projectDescription" id="pDescription" class="inputDLP NP description" 
									title="Enter description of project here" maxlength="2005" />
								</textarea>
							</div>
						</div>
					
						<div class="fieldgroup manager">
							<div class="headline">
								Manager:
							</div>
							<select name="projectMCombobox" id="pMCombobox" class="inputDLP NP managerCombobox">
								<option></option>
							</select>
						</div>
						
						<div class="fieldgroup col">
							<div class="headline" >
								Date started:
							</div>
							<div class="fields" >
								<input name="projectDStarted" id="pDateStarted" class="inputDLP NP">
							</div>
						</div>
				
						<div class="fieldgroup col">
							<div class="headline" >
								Deadline:
							</div>
							<div class="fields">
								<input name="projectDDeadline" id="pDeadline" class="inputDLP NP">
							</div>
						</div>
											
						<div class="fieldgroup col floatRight">
							<div class="headline" >
								Date finished:
							</div>
							<div class="fields">
								<input name="projectDFinished" id="pDateFinished" class="inputDLP NP">
							</div>
						</div>
				
						
						<br>
						<button id="savePrjButton" class="buttonPB saveButton">SAVE</button>
						<button id="cancelButton" class="buttonPB cancelButton">CANCEL</button>
					</form>
				</div>
			</div>
		</div>
		
		<!-- TASK DIALOG FORM  -->
		<div id="dialog-form-task" class="dialog" style="padding: 0px">
			<div class="closeButton">
            </div>
			<div class="inner">
				<div class="task-box boxes">
					<form id="taskForm">
						<div class="fieldgroup">
                            <div id="taskDateCreated" class="inputDLP EP"></div>
							<div class="headline" >
								Title:
							</div>
							<div class="fields">
								<input name="taskTitle" id="tTitle" class="inputDLP NP title" 
									title="Enter title of task here" maxlength="75" />
							</div>
						</div>
						
						<div class="fieldgroup description">
							<div class="headline" >
								Description:
							</div>
							<div class="fields description-field">
								<textarea name="taskDescription" id="tDescription" class="inputDLP NP description" 
									title="Enter description of project here" maxlength="3000" />
								</textarea>
							</div>
						</div>
							
						<div class="fieldgroup proj" >
								<div class="headline" >
									Project:
								</div>
								<select name="taskProject" id="tProjectCombobox" class="inputDLP NP taskPrj">
									<option></option>
								</select>
						</div>
							
						<div class="datepickers" >
							<div class="fieldgroup col" >
								<div class="headline" >	
									Date started:
								</div>
								<div class="fields" >
									<input name="taskDateStarted" id="tDateStarted" class="inputDLP NP">
								</div>
							</div>
						
							<div class="fieldgroup col" >
								<div class="headline" >
									Deadline:
								</div>
								<div class="fields">
									<input name="taskDeadline" id="tDeadline" class="inputDLP NP">
								</div>
							</div>
							
							<div class="fieldgroup col floatRight" >
								<div class="headline" >
									Date finished:
								</div>
								<div class="fields">
									<input name="taskDateFinished" id="tDateFinished" class="inputDLP NP">
								</div>
							</div>
						</div>
						
						<div class="dropBoxes" >
							<div class="fieldgroup col" >
								<div class="headline">
									Status:
								</div>
								<select name="taskStatusCombobox" id="tStatusCombobox" class="inputDLP NP">
									<option></option>
								</select>
							</div>
							
							<div class="fieldgroup col" >
								<div class="headline">
									Priority:
								</div>
								<select name="takePriorityCombobox" id="tPriorityCombobox" class="inputDLP NP">
									<option></option>
								</select>
							</div>
							
							<div class="fieldgroup col floatRight" >
								<div class="headline">
									Executive:
								</div>
								<select name="taskExCombobox" id="tExCombobox" class="inputDLP NP">
									<option></option>
								</select>
							</div>
						</div>
					</form>
										
					
					<br>
					<button id="saveTaskButton" class="buttonPB saveButton">SAVE</button>
					<button id="cancelButton" class="buttonPB cancelButton">CANCEL</button>
				</div>
			</div>
		</div>
		
		<!-- SCHEDULER DIALOG FORM  -->
		<div id="dialog-diary-entry" class="dialog" style="padding: 0px">
			<div class="closeButton">
            </div>
			<div class="inner">
				<div class="diary-box boxes">
					<form id="diaryForm">						
						<div class="fieldgroup">
							<div class="headline" >
								Event name:
							</div>
							<div class="fields">
								<input name="entryTitle" id="eTitle" class="inputDLP NA title" maxlength="75" />
							</div>
                        </div>	
						
						<div class="fieldgroup col datepickers" >
							<div class="headline" >
								Date started:
							</div>
							<div class="fields" >
								<input name="entryDateStarted" id="eDateStarted" class="inputDLP NP hasDatepicker "/>
							</div>
						</div>
												
						<div class="fieldgroup col datepickers" >
							<div class="headline" >
								Date finished:
							</div>
							<div class="fields">
								<input name="entryDateFinished" id="eDateFinished" class="inputDLP NP hasDatepicker "/>
							</div>
						</div>						
					
						<br>
						<button id="saveEntryButton" class="buttonPB saveButton">SAVE</button>
                        <button id="delEntryButton" class="buttonPB delButton">DELETE</button>
                        <button id="cancelButton" class="buttonPB cancelButton">CANCEL</button>
					</form>
				</div>
			</div>
		</div>		
				
		<!--ANNOUNCEMENT DIALOG FORM  -->
		<div id="dialog-form-announcement" class="dialog" style="padding: 0px">
			<div class="closeButton">
            </div>
			<div class="inner">
				<div class="announcement-box boxes">
					<form id="annForm">						
						<div class="fieldgroup">
							<div class="headline title" >
								Title:
							</div>
							<div class="fields">
								<input name="annTitle" id="aTitle" class="inputDLP NA title" maxlength="75" />
							</div>
                        </div>						

						<div class="fieldgroup">
							<div class="headline " >
								Description:
							</div>
							<div class="fields">
								<textarea name="annDescr" id="aDescription" class="inputDLP NA description" 
									title="Enter description of task here" maxlength="2000" />
								</textarea>
							</div>
						</div>
						
						<div class="fieldgroup">
							<div class="headline file" >
								Attachment (max 15 Mb):
							</div>
							<div class="fields file-field">
								<input name="annFile" type="file" id="aFile" class="inputDLP NA"/> <br/>
								<label for="aFile" id="fileError" class="error"></label>
							</div>
						</div>
						<button id="saveAnnButton" class="buttonPB saveButton">SAVE</button>
						<button id="cancelButton" class="buttonPB cancelButton">CANCEL</button>
					</form>
				</div>
			</div>
		</div>

        <!--USER DIALOG FORM  -->
        <div id="dialog-form-user" class="dialog" style="padding: 0px">
            <div class="closeButton">
            </div>
            <div class="inner">
                <div class="user-box boxes">
                    <form id="userForm">
                        <div class="fieldgroup col">
                            <div class="headline" >
                                Name:
                            </div>
                            <div class="fields">
                                <input name="userNameField" id="uName" class="inputDLP NP"
                                       title="Enter user name here" maxlength="50" />
                            </div>
                        </div>

                        <div class="fieldgroup col">
                            <div class="headline" >
                                Surname:
                            </div>
                            <div class="fields">
                                <input name="userSurnameField" id="uSurname" class="inputDLP NP"
                                       title="Enter user surname here" maxlength="60" />
                            </div>
                        </div>

                        <div class="fieldgroup col">
                            <div class="headline" >
                                Email:
                            </div>
                            <div class="fields">
                                <input name="userEmailField" id="uEmail" class="inputDLP NP"
                                       autocomplete="off" title="Enter user email here" maxlength="100" />
                            </div>
                        </div>

                        <div class="fieldgroup col">
                            <div class="headline" >
                                Password:
                            </div>
                            <div class="fields">
                                <input name="userPasswordField" id="uPassword" class="inputDLP NP"
                                       type="password" autocomplete="off" maxlength="100" />
                                <span class="shp">
                                    <input type="checkbox" id="showHidePassword">Show password
                                </span>
                            </div>
                        </div>

                        <div class="fieldgroup col">
                            <div class="headline" >
                                Position:
                            </div>
                            <div class="fields">
                                <input name="userPositionField" id="uPosition" class="inputDLP NP"
                                       title="Enter user position here" maxlength="100" />
                            </div>
                        </div>

                        <div class="fieldgroup col">
                            <div class="headline" >
                                Phone number:
                            </div>
                            <div class="fields">
                                <input name="userPhoneNumField" id="uPhoneNum" class="inputDLP NP"
                                       title="Enter user phone number here" maxlength="30" />
                            </div>
                        </div>

                        <div class="fieldgroup col" >
                            <div class="headline">
                                Role:
                            </div>
                            <select name="userRoleCombobox" id="uRoleCombobox" class="inputDLP NP roleDroplist">
                                <option value="">Select user role</option>
                            </select>
                        </div>

                        <div class="fieldgroup col" >
                            <div class="headline" >
                                Birth Date:
                            </div>
                            <div class="fields" >
                                <input name="userBirthDateField" id="uBirthDate" class="inputDLP NP hasDatepicker">
                            </div>
                        </div>

                        <div class="fieldgroup col">
                            <div class="headline" >
                                Address:
                            </div>
                            <div class="fields">
                                <input name="userAddressField" id="uAddress" class="inputDLP NP userAddress"
                                       title="Enter user address number here" maxlength="30" />
                            </div>
                        </div>

                        <button id="saveUserButton" class="buttonPB saveButton">SAVE</button>
                        <button id="cancelButton" class="buttonPB cancelButton">CANCEL</button>
                    </form>
                </div>
            </div>
        </div>

        <!--COMPANY FILES ADD/EDIT SECTION DIALOG FORM  -->
        <div id="dialog-form-section" class="dialog" style="padding: 0px">
            <div class="closeButton">
            </div>
            <div class="inner">
                <div class="section-box boxes">
                    <form id="sectionForm">
                        <div class="fieldgroup">
                            <div class="headline" >
                                Section name:
                            </div>
                            <div class="fields">
                                <input name="sectionName" id="sName" class="inputDLP NA title" maxlength="75" />
                            </div>
                        </div>
                        <button id="saveSectionButton" class="buttonPB saveButton">SAVE</button>
                        <button id="cancelButton" class="buttonPB cancelButton">CANCEL</button>
                    </form>
                </div>
            </div>
        </div>

        <!--COMPANY FILES SECTION DIALOG FORM  -->
        <div id="dialog-form-section-file" class="dialog" style="padding: 0px">
            <div class="closeButton">
            </div>

            <div class="inner">
                <div class="files-box boxes">
                    <button id="addFileButton" class="buttonPB addButton">NEW</button>
                    <button id="delFileButton" class="buttonPB delButton">DELETE</button>
                    <form id="fileForm">
                        <div class="fieldgroup">
                            <div class="fields file-field">
                                <button id="saveFileButton" class="buttonPB addButton">Save to list</button>
                                <input name="newFile" type="file" id="sFile" class="inputDLP NA"/> <br/>
                                <label for="aFile" class="error"></label>
                            </div>
                        </div>
                    </form>
                    <div class="files-table">
                    </div>

                </div>
            </div>
        </div>

		<!--GENERAL DELETE DIALOG FORM  -->
		<div id="dialog-form-delete" class="dialog" style="padding: 0px">
			<div class="closeButton">
            </div>
			<div class="inner">
				<div class="delete-box boxes">
					<div class="headline title" >
						Are you sure that you want to delete selected items?
					</div>
					
					<br>
					<button id="okDelButton" class="buttonPB okButton">OK</button>
					<button id="cancelButton" class="buttonPB">CANCEL</button>
				</div>
			</div>
		</div>
		
		<!--GENERAL ERROR DIALOG FORM  -->
		<div id="dialog-form-error" class="dialog" style="padding: 0px;">
			<div class="closeErrButton">
            </div>
			<div class="inner">
				<div class="error-box boxes">
					<div class="headline title" id="errContent">

					</div>
					<br>
					<button id="okErrButton" class="buttonPB okButton">OK</button>
				</div>
			</div>
		</div>
	</body>
</html>
