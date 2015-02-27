$(window).on("resize", function () {
    var $grid = $("#projectsTable"),
        newWidth = $grid.closest(".ui-jqgrid").parent().width();
    $grid.jqGrid("setGridWidth", newWidth, true);
});
$(window).on("resize", function () {
    var $grid = $("#tasksTable"),
        newWidth = $grid.closest(".ui-jqgrid").parent().width();
    $grid.jqGrid("setGridWidth", newWidth, true);
});
$(document).ready(function(){
    $('li.parent').hover(function () {
        if ($(this).find('> ul').css('display') == "none") {
            $(this).find('> ul').slideDown(200);
            slide = true;
        }
    }, function () {
        if (slide == true) {
            $(this).find('> ul').slideUp();
            slide = false;
        }
    });
    $('nav strong').click(function () {
        $('nav ul').toggle();
    });
});

// SCHEDULER PAGE SECTION
$(document).ready(function(){
    var calendar = null;
    var requestType = "";
    $("#cancelButton").prop("disabled",false);
    calendar = $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        height: 700,
        firstHour: 8,
        selectable: true,
        selectHelper: true,
        editable: true,
        ignoreTimezone: false,
        timezone: 'local',
        columnFormat: {
            month: 'ddd',
            week: 'ddd d/M',
            day: 'dddd d/M'
        },
        select: function(start, end, allDay) {
            allDay = false;
            requestType = "POST";
            $("#dialog-diary-entry").data("entry", getEntryObj(start, end, null));
            $("#delEntryButton").prop("disabled",true);
            $("#dialog-diary-entry").dialog( "open" );

            calendar.fullCalendar('unselect');
        },
        eventClick: function(calEvent, jsEvent) {
            calendar.fullCalendar('gotoDate', calEvent.start);

            requestType = "PUT";
            $("#dialog-diary-entry").data("entry", getEntryObj(calEvent.start, calEvent.end, calEvent));
            $("#delEntryButton").prop("disabled",false);
            $("#dialog-diary-entry").dialog( "open" );
        },
        eventResize: function( event, delta, revertFunc, jsEvent) {
            requestType = "PUT";
            var editedEntry =  getEntryObj(event.start, event.end, event);
            sendEntry(editedEntry);
        },
        eventDrop: function( event, delta, revertFunc, jsEvent)  {
            requestType = "PUT";
            var editedEntry =  getEntryObj(event.start, event.end, event);
            sendEntry(editedEntry);
        },
        events: {
            url: '/pb/ajax/scheduler?' + getCookie("userId"),
            ignoreTimezone: false,
            timezone: 'local',
            type: 'GET',
            error: function(data) {
                $("#errContent").append('Failed to load events. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        },
        eventColor: '#424250'
    });

    $( "#dialog-diary-entry" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        open: function(){
            $(this).css('overflow', 'hidden');
            var entry = $(this).data("entry");
            if (entry != null) {
                $('#eTitle').val(entry.title);
                $('#eDateStarted').val(entry.start);
                $("#eDateFinished").val(entry.end);
            }
            else{
                $('#eTitle').val('');
                $('#eDateStarted').val('');
                $("#eDateFinished").val('');
            }
        },
        close: function(){
            $("label.error").hide();
        }
    });

    $( "#eDateStarted, #eDateFinished" ).datetimepicker({
        format:'unixtime',
        format:'m-d-Y H:i:s',
        startDate:'m/d/01'
    });

    $( "#newEntryDiaryButton" ).click(function() {
        requestType = "POST";
        $( "#dialog-diary-entry" ).data("entry", null);
        $("#delEntryButton").prop("disabled",true);
        $( "#dialog-diary-entry" ).dialog( "open" );
    });

    $( "#saveEntryButton" ).click(function(e) {
        e.preventDefault();
        e.stopPropagation();

        if($("#diaryForm").valid()){
            if (sendEntry(getEntryFromForm())) {
                $("#dialog-diary-entry").dialog("close");
            }
        }
    });

    $( "#delEntryButton" ).click(function(e) {
        if($("#diaryForm").valid()){
            if (deleteEntry(getEntryFromForm())) {
                $("#dialog-diary-entry").dialog("close");
            }
        }
    });

    function sendEntry(entry) {
        var success = true;
        var entryInJson = JSON.stringify(entry);
        $.ajax({
            url: '/pb/ajax/scheduler',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            type: requestType,
            data: entryInJson,
            success: function (data) {
                $("#calendar").fullCalendar("refetchEvents");
            },
            error: function (data) {
                success = false;
                var failedAction = (requestType == "POST") ? 'add' : 'edit';
                $("#errContent").append('Failed to ' + failedAction + ' diary entry. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    function getEntryFromForm(){
        var entry = {};
        entry.id = ($("#dialog-diary-entry").data("entry")).id;
        entry.user = {};
        entry.user.id = getCookie("userId");
        entry.title = $('#eTitle').val();
        entry.start = new Date($('#eDateStarted').val());
        entry.end = new Date($("#eDateFinished").val());

        return entry;
    };

    function getEntryObj(start, end, calEvent) {
        var entry = {};
        entry.id = calEvent == null ? '' : calEvent.id;
        entry.user = {};
        entry.user.id = getCookie("userId");
        entry.title = calEvent == null ? '' : calEvent.title;
        entry.start = new Date(start);
        entry.end = new Date(end);

        return entry;
    };

    function deleteEntry(entry) {
        var success = true;
        $.ajax({
            url: '/pb/ajax/scheduler/' + entry.id,
            async: false,
            type: 'DELETE',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                $("#calendar").fullCalendar("refetchEvents");
            },
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete entry. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
                $("#dialog-form-delete").dialog( "close" );
            }
        });
        return success;
    };

    $.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            var d2 = $(params).val();
            return new Date(value) > new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    });

    $("#diaryForm").validate({
        rules: {
            entryTitle: {
                required: true,
                minlength: 2,
                maxlength: 60
            },
            entryDateStarted:{
                required: true
            },
            entryDateFinished: {
                required: true,
                greaterThan: "#eDateStarted"
            }
        },
        messages: {
            entryTitle: {
                required: "Entry title is required",
                minlength: $.validator.format("At least 0 characters required in \"Title\" field"),
                maxlength: $.validator.format("Name length should not exceed 40 characters")
            },
            entryDateStarted: {
                required: "Date started is required"
            },
            entryDateFinished: {
                required: "Date finished is required",
                greaterThan: "Must be greater then Date started"
            }
        }
    });

    function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    };
});

// PROJECT PAGE SECTION
$(document).ready(function(){
	var requestType = "";

    $("#projectsTable").jqGrid({
		url:'/pb/ajax/project',
		datatype: "json",
        type: 'GET',
		height: 550,
        autowidth: true,
        forceFit: true,
		rowNum:20,
		rowList:[20,40,80],
		sortname: "Created",
		sortorder: "desc",
		pager: "#pagingDivProjects",
		viewrecords: true,
		colNames:['Id','Title','Description','Created', 'Started',
				    'Deadline','Finished','ManagerId','ManagerName','Manager'],
		colModel:[ 
            {label:'Id',name:'id', hidden:true},
            {label:'Title',name:'name', width:125, resizable:true,sortable:false},
            {label:'Description',name:'description', width:230, resizable:true, align:"left", sortable:false},
            {label:'Created',name:'dateCreation', width:85, resizable:true, sortable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'} },
            {label:'Started',name:'dateStarted', width:85, resizable:true, sortable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'} },
            {label:'Deadline',name:'deadline', width:85, resizable:true, sortable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'}},
            {label:'Finished',name:'dateFinished', width:85, resizable:true, sortable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'}},
            {label:'ManagerId',name:'manager.id', hidden:true},
            {label:'ManagerName',name:'manager.name', hidden:true},
            {label:'Manager',name:'manager.surname', width:100, resizable:true, sortable:true}
		],
		shrinkToFit:true,
		multiselect: true
	});

    $( "#dialog-form-project" ).dialog({
		autoOpen: false,
		width: 'auto',
		modal: true,
        resizable: false,
        fluid: true,
		dialogClass: 'dialog',
		open: function() {
            $(this).css('overflow', 'hidden');
			var project = $(this).data("project");
			if (project != null) {
				requestType = "PUT";

				$("#projectDateCreated").text( "created: " + getDateFormatted(project.dateCreation));
				$("#pTitle").val(project.name);
				$("#pDescription").val(project.description);

                $( "#pDateStarted" ).datepicker( "option", "minDate", project.dateStarted);
                $( "#pDeadline" ).datepicker( "option", "minDate", project.dateStarted);
                $( "#pDateFinished" ).datepicker( "option", "minDate", project.dateStarted);

				$('#pDateStarted').datepicker("setDate", project.dateStarted);
				$('#pDeadline').datepicker("setDate", project.deadline);
                if(project.dateFinished != "Invalid Date"){
				    $('#pDateFinished').datepicker("setDate", project.dateFinished );
                }

                $("#pMCombobox").val(project.manager.name + ' ' + project.manager.surname);
			}else{
				requestType = "POST";
			}
		},
		close: function(){
            $("#pDateStarted, #pDeadline").datepicker( "option", "minDate", -0 );
			$(".inputDLP.NP").val('');
			$("label.error").hide();
			$("#projectDateCreated").empty();
		}
	});

    $( "#pDateStarted, #pDateFinished, #pDeadline" ).datepicker({
        minDate: -0,
        dateFormat: "yy-mm-dd"
    });

    $.getJSON( "/pb/ajax/user", function( data ) {
        $.each( data, function( key, val ) {
            $("#pMCombobox").append( "<option id='manager_" + val.id + "'>" + val.name + ' ' + val.surname + "</option>" );
        });
    });

    $("#pDateFinished" ).click(function(){
        $( "#dialog-form-project" ).data("dFinished_changed", true);
    });

	$( "#addProjectButton" ).click(function() {
		$( "#dialog-form-project" ).data("project", null);
		$( "#dialog-form-project" ).data("dFinished_changed", false);
		$( "#dialog-form-project" ).dialog( "open" );		
	});
	
	$( "#editProjectButton" ).click(function() {
        if(getChosenElemsInGrid().length != 0) {
            var projectToEdit = getProjFromGridCell();
            $( "#dialog-form-project" ).data("project", projectToEdit);
            $( "#dialog-form-project" ).data("dFinished_changed", false);
            $( "#dialog-form-project" ).dialog( "open" );
        }else {
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
	});
	
	$( "#deleteProjectButton" ).click(function() {
        var chosenElems = getChosenElemsInGrid();
        if(chosenElems.length != 0) {
            $( "#dialog-form-delete" ).data("projectArr", chosenElems);
            $( "#dialog-form-delete" ).dialog( "open" );
        }else {
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
	});

	$( "#savePrjButton" ).click(function(e) {
        e.preventDefault();
        e.stopPropagation();
		if($("#projectForm").valid()){
			if (sendProject(getProjFromForm())) {
				$('#dialog-form-project').dialog('close');
				$('#projectsTable').trigger( 'reloadGrid' );
			}
		}
	});

    $( "#okDelButton" ).click(function(){
        if($(".project-container").is(":visible")){
            var projectsToDel = $('#dialog-form-delete').data("projectArr");
            if(deleteProject(projectsToDel)){
                $("#projectsTable").trigger('reloadGrid');
                $("#dialog-form-delete").data("projectArr", null);
                $("#dialog-form-delete").dialog('close');
            }
        }
    });

    function getChosenElemsInGrid(){
        return $('#projectsTable').jqGrid('getGridParam','selarrrow');
    };

	function sendProject(project) {
		var success = true;
		var projInJson = JSON.stringify(project);
		$.ajax({
			url: '/pb/ajax/project',
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: false,
			type: requestType,
			data: projInJson,
			error: function (data) {
                var action = (requestType == "POST") ? 'add' : 'edit';
				success = false;
				$("#errContent").append('Failed to ' + action + ' project. ' + data.statusText);
				$("#dialog-form-error").dialog( "open" );
			}
		});
		return success;
	};

    function deleteProject(prArrToDel) {
        var success = true;
        var projArrInJson = JSON.stringify(prArrToDel);
        $.ajax({
            url: '/pb/ajax/project',
            async: false,
            type: 'DELETE',
            data: projArrInJson,
            contentType: "application/json; charset=utf-8",
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete project. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
                $("#dialog-form-delete").dialog( "close" );
            }
        });
        return success;
    };

    function getProjFromGridCell() {
        var project = {};

        var selr = $('#projectsTable').jqGrid ('getGridParam','selrow');
        project.id = $('#projectsTable').jqGrid ('getCell', selr, 'id');
        project.dateCreation = $('#projectsTable').jqGrid ('getCell', selr, 'dateCreation');
        project.name = $('#projectsTable').jqGrid ('getCell', selr, 'name');
        project.description = $('#projectsTable').jqGrid ('getCell', selr, 'description');
        project.dateStarted = getValidDate($('#projectsTable').jqGrid ('getCell', selr, 'dateStarted'));
        project.deadline = getValidDate($('#projectsTable').jqGrid ('getCell', selr, 'deadline'));
        project.dateFinished = $('#projectsTable').jqGrid ('getCell', selr, 'dateFinished') == '' ?
                                        '' : getValidDate($('#projectsTable').jqGrid ('getCell', selr, 'dateFinished'));
        project.manager = {};
        project.manager.id = $('#projectsTable').jqGrid ('getCell', selr, 'manager.id');
        project.manager.name = $('#projectsTable').jqGrid ('getCell', selr, 'manager.name');
        project.manager.surname = $('#projectsTable').jqGrid ('getCell', selr, 'manager.surname');

        return project;
    };

	function getProjFromForm() {

		var project = $("#dialog-form-project").data("project") ? $("#dialog-form-project").data("project") : {};
        if($("#dialog-form-project").data("project")){
            project.id = project.id;
            project.dateCreation = project.dateCreation;
        }else{
            project.dateCreation = new Date();
        }

		project.name = $('#pTitle').val();
		project.description = $('#pDescription').val();
		project.dateStarted = getValidDate($('#pDateStarted').val());
		project.deadline = getValidDate($('#pDeadline').val());
		project.dateFinished = $('#pDateFinished').val() == '' ? null : getValidDate($('#pDateFinished').val());

        project.manager = {};
        project.manager.id = $($('#pMCombobox').children(':selected')[0]).attr("id").substring("manager_".length);
		
		return project;
	};

    $.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            var d2 = $(params).val();
            return new Date(value) >= new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    });
	
	$("#projectForm").validate({
		rules: {
			projectTitle: {
				required: true,
				minlength: 2,
				maxlength: 75
			},
			projectDescription: {
				maxlength: 2000
			},
			projectDStarted:{
				required: true,
				date: true
			},
			projectDDeadline: {
				required: true,
				date: true,
				greaterThan: "#pDateStarted"
			},
			projectDFinished: {
				date: true,
				greaterThan:{
					param: "#pDateStarted",
					depends: function(element) {
						return $( "#dialog-form-project" ).data("dFinished_changed");
					}
 				}
			},
			projectMCombobox: {
				required: {
					depends: function(element) {
						return $("#pMCombobox").val() == '';
					}
				}
			}
		},
		messages: {
			projectTitle: {
				required: "This field is required",
				minlength: $.validator.format("At least {0} characters required in \"Name\" field"),
				maxlength: $.validator.format("Name length should not exceed {0} characters")
			},
			projectDescription: {
				maxlength: $.validator.format("Description length should not exceed {0} characters")
			},
			projectDStarted: {
				required: "This field is required",
				date: "Please enter a valid date"
			},
			projectDDeadline: {
				required: "This field is required",
				greaterThan: "Must be greater then \"Date started\"",
				date: "Please enter a valid date."
			},
			projectDFinished: {
				greaterThan: "Must be greater then \"Date started\"",
				date: "Please enter a valid date"
			},
			projectMCombobox: {
				required: "This field is required"
			}
		}
	});

	$( "#dialog-form-delete" ).dialog({
        autoOpen: false,
        resizable: false,
        width: 'auto',
        modal: true,
        dialogClass: 'deleteDialog',
        fluid: true,
        open: function() {
            $(this).css('overflow', 'hidden');
        }
	});
	
	$( "#dialog-form-error" ).dialog({
		autoOpen: false,			
		resizable: false,
		width: 'auto',
		modal: true,
		dialogClass: 'errorDialog',
        fluid: true,
        open: function() {
            $(this).css('overflow', 'hidden');
        },
		close: function(){
			$("#errContent").empty();
		}
	});
	
	$( ".closeButton, #cancelButton" ).click(function(e){
        e.preventDefault();
        e.stopPropagation();
		$("#dialog-form-project").dialog('close');
		$("#dialog-form-task").dialog('close');
		$("#dialog-form-announcement").dialog('close');
		$("#dialog-diary-entry").dialog('close');
        $("#dialog-form-section").dialog('close');
        $("#dialog-form-section-file").dialog('close');
        $("#dialog-form-user").dialog('close');
		$("#dialog-form-delete").dialog('close');
	});

    $( ".closeErrButton, #okErrButton" ).click(function(e){
        $("#dialog-form-error").dialog('close');
    });

    function getDateFormatted(dateStr) {
        var d = new Date(dateStr);
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        if (day < 10) {
            day = "0" + day;
        }
        if (month < 10) {
            month = "0" + month;
        }
        var date = month + "/" + day + "/" + year;
        return date;
    };

    function getValidDate(initialDate){
        var arrDate = initialDate.split('-');
        return new Date(arrDate[0], arrDate[1]-1, arrDate[2], 0, 0, 0, 0);
    };
});

// TASK PAGE SECTION
$(document).ready(function(){
    var requestType = "";

    $("#tasksTable").jqGrid({
        url:'/pb/ajax/task',
        datatype: "json",
        type: 'GET',
        height: 550,
        autowidth: true,
        forceFit: true,
        rowNum:20,
        rowList:[20,40,80],
        sortname: "Created",
        sortorder: "desc",
        pager: "#pagingDivTasks",
        viewrecords: true,
        colNames:[  'Id', 'Title','Description', 'Created','Started','Deadline','Finished', 'StatusId',
            'Status','PriorityId','Priority','ProjectId','Project',
            'ManagerId','ManagerName','Manager','ExecutiveId','ExecutiveName','Executive'],
        colModel:[
            {label:'Id',name:'id', width:1, resizable:false,sortable:false, hidden:true},
            {label:'Title',name:'name', width:130, resizable:true},
            {label:'Description',name:'description', width:185, resizable:true},
            {label:'Created',name:'dateCreation', width:90, resizable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'}},
            {label:'Started',name:'dateStarted', width:90, resizable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'}},
            {label:'Deadline',name:'deadline', width:90, resizable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'}},
            {label:'Finished',name:'dateFinished', width:90, resizable:true, sorttype:"date", formatter : 'date', formatoptions : {newformat : 'Y-m-d'}},

            {label:'StatusId',name:'status.id', hidden:true},
            {label:'Status',name:'status.name', width:70, resizable:true, sortable:true},

            {label:'PriorityId',name:'priority.id', hidden:true},
            {label:'Priority',name:'priority.name', width:70, resizable:true, sortable:true},

            {label:'ProjectId',name:'project.id', hidden:true},
            {label:'Project',name:'project.name', width:150, resizable:true, sortable:true},

            {label:'ManagerId',name:'manager.id', hidden:true},
            {label:'ManagerName',name:'manager.name', hidden:true},
            {label:'Manager',name:'manager.surname', width:100, sortable:true},

            {label:'ExecutiveId',name:'executive.id', hidden:true},
            {label:'ExecutiveName',name:'executive.name', hidden:true},
            {label:'Executive',name:'executive.surname', width:100, resizable:true, sortable:true}
        ],
        shrinkToFit:true,
        multiselect: true
    });

    $( "#dialog-form-task" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        open: function() {
            $(this).css('overflow', 'hidden');

            var task = $(this).data("task");
            if (task != null) {
                requestType = "PUT";

                $("#taskDateCreated").text( "created: " + getDateFormatted(task.dateCreation));

                $("#tTitle").val(task.name);
                $("#tDescription").val(task.description);

                $( "#tDateStarted" ).datepicker( "option", "minDate", task.dateStarted );
                $( "#tDeadline" ).datepicker( "option", "minDate", task.dateStarted );
                $( "#tDateFinished" ).datepicker( "option", "minDate", task.dateStarted );

                $('#tDateStarted').datepicker("setDate", task.dateStarted );
                $('#tDeadline').datepicker("setDate", task.deadline );
                if(task.dateFinished != "Invalid Date"){
                    $('#tDateFinished').datepicker("setDate",  task.dateFinished );
                }

                $("#tStatusCombobox").val(task.status.name);
                $("#tPriorityCombobox").val(task.priority.name);

                $("#tProjectCombobox").val(task.project.name);
                $("#tExCombobox").val(task.executive.name + ' ' + task.executive.surname);

            }else{
                requestType = "POST";
            }
        },
        close: function(){
            $( "#tDateStarted, #tDeadline" ).datepicker( "option", "minDate", -0 );
            $(".inputDLP.NP").val('');
            $("label.error").hide();
            $( "#projectDateCreated" ).empty();
        }
    });

    $( "#tDateStarted, #tDateFinished, #tDeadline" ).datepicker({
        minDate: -0,
        dateFormat: "yy-mm-dd"
    });

    $( "#tDateFinished" ).click(function(e){
        $( "#dialog-form-task" ).data("taskDFinished_changed", true);
    });

    $.getJSON( "/pb/ajax/status", function( data ) {
        $.each( data, function( key, val ) {
            $("#tStatusCombobox").append( "<option id='status_" + val.id + "'>" + val.name + "</option>" );
        });
    });

    $.getJSON( "/pb/ajax/priority", function( data ) {
        $.each( data, function( key, val ) {
            $("#tPriorityCombobox").append( "<option id='priority_" + val.id + "'>" + val.name + "</option>" );
        });
    });

    $.getJSON( "/pb/ajax/project/all", function( data ) {
        $.each( data, function( key, val ) {
            $("#tProjectCombobox").append( "<option id='project_" + val.id + "'>" + val.name + "</option>" );
        });
    });

    $.getJSON( "/pb/ajax/user", function( data ) {
        $.each( data, function( key, val ) {
            $("#tExCombobox").append( "<option id='executive_" + val.id + "'>" + val.name + ' ' + val.surname +  "</option>" );
        });
    });

    function getTaskFromGridCell() {
        var selr = 	$('#tasksTable').jqGrid ('getGridParam','selrow');

        var task = {};
        task.id = $('#tasksTable').jqGrid ('getCell', selr, 'id');
        task.name = $('#tasksTable').jqGrid ('getCell', selr, 'name');
        task.description = $('#tasksTable').jqGrid ('getCell', selr, 'description');
        task.dateCreation = getValidDate($('#tasksTable').jqGrid ('getCell', selr, 'dateCreation'));
        task.dateStarted = 	getValidDate($('#tasksTable').jqGrid ('getCell', selr, 'dateStarted'));
        task.deadline = getValidDate($('#tasksTable').jqGrid ('getCell', selr, 'deadline'));
        task.dateFinished = $('#tasksTable').jqGrid ('getCell', selr, 'dateFinished') == '' ?
            '' : getValidDate($('#tasksTable').jqGrid ('getCell', selr, 'dateFinished'));
        task.status = {};
        task.status.id = $('#tasksTable').jqGrid ('getCell', selr, 'status.id');
        task.status.name = $('#tasksTable').jqGrid ('getCell', selr, 'status.name');

        task.priority = {};
        task.priority.id = $('#tasksTable').jqGrid ('getCell', selr, 'priority.id');
        task.priority.name = $('#tasksTable').jqGrid ('getCell', selr, 'priority.name');

        task.manager = {};
        task.manager.id = $('#tasksTable').jqGrid ('getCell', selr, 'manager.id');
        task.manager.name = $('#tasksTable').jqGrid ('getCell', selr, 'manager.name');
        task.manager.surname = $('#tasksTable').jqGrid ('getCell', selr, 'manager.surname');

        task.executive = {};
        task.executive.id = $('#tasksTable').jqGrid ('getCell', selr, 'executive.id');
        task.executive.name = $('#tasksTable').jqGrid ('getCell', selr, 'executive.name');
        task.executive.surname = $('#tasksTable').jqGrid ('getCell', selr, 'executive.surname');

        task.project = {};
        task.project.id = $('#tasksTable').jqGrid ('getCell', selr, 'project.id');
        task.project.name = $('#tasksTable').jqGrid ('getCell', selr, 'project.name');

        return task;
    };

    function getTaskFromForm() {
        var task = $("#dialog-form-task").data("task") ? $("#dialog-form-task").data("task") : {};

        if($("#dialog-form-task").data("task")){
            task.id = ($("#dialog-form-task").data("task")).id;
            task.dateCreation = ($("#dialog-form-task").data("task")).dateCreation;
        }else{
            task.dateCreation = new Date();
        }
        task.name = $('#tTitle').val();
        task.description = $('#tDescription').val();

        task.dateStarted = 	getValidDate($('#tDateStarted').val());
        task.deadline = getValidDate($('#tDeadline').val());
        task.dateFinished = $('#tDateFinished').val() == '' ?
            null : getValidDate($('#tDateFinished').val());

        task.status = {};
        task.status.id = $($('#tStatusCombobox').children(':selected')[0]).attr("id").substring("status_".length);

        task.priority = {};
        task.priority.id = $($('#tPriorityCombobox').children(':selected')[0]).attr("id").substring("priority_".length);

        task.manager = {};
        task.manager.id = getCookie("userId");

        task.executive = {};
        task.executive.id = $($('#tExCombobox').children(':selected')[0]).attr("id").substring("executive_".length);

        task.project = {};
        task.project.id = $($('#tProjectCombobox').children(':selected')[0]).attr("id").substring("project_".length);

        return task;
    };

    function getChosenElemsInGrid(){
        return $('#tasksTable').jqGrid('getGridParam','selarrrow');
    };

    $( "#addTaskButton" ).click(function() {
        $( "#dialog-form-task" ).data("task", null);
        $( "#dialog-form-task" ).data("taskDFinished_changed", false)
        $( "#dialog-form-task" ).dialog( "open" );
    });

    $( "#editTaskButton" ).click(function() {
        if(getChosenElemsInGrid().length != 0) {
            var taskToEdit = getTaskFromGridCell();
            $( "#dialog-form-task" ).data("task", taskToEdit);
            $( "#dialog-form-task" ).data("taskDFinished_changed", false);
            $( "#dialog-form-task" ).dialog( "open" );
        }else {
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
    });

    $( "#delTaskButton" ).click(function() {
        var chosenElems = getChosenElemsInGrid();
        if(chosenElems.length != 0) {
            $( "#dialog-form-delete" ).data("taskArr", chosenElems);
            $( "#dialog-form-delete" ).dialog( "open" );
        }else {
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
    });

    $( "#saveTaskButton" ).click(function(e) {
        e.preventDefault();
        e.stopPropagation();
        if($("#taskForm").valid()){
            var taskToSend = getTaskFromForm();
            if (sendTask(taskToSend)) {
                $('#dialog-form-task').dialog('close');
                $('#tasksTable').trigger( 'reloadGrid' );
            }
        }
    });

    $( "#okDelButton" ).click(function(){
        if($(".task-container").is(":visible")){
            var tasksToDel = $('#dialog-form-delete').data("taskArr");
            if(deleteTask(tasksToDel)){
                $("#dialog-form-delete").data("taskArr", null);
                $("#dialog-form-delete").dialog('close');
                $('#tasksTable').trigger( 'reloadGrid' );
            }
        }
    });

    function sendTask(task) {
        var success = true;
        var taskInJson = JSON.stringify(task);
        $.ajax({
            url: '/pb/ajax/task',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            type: requestType,
            data: taskInJson,
            error: function (data) {
                var action = (requestType == "POST") ? 'add' : 'edit';
                success = false;
                $("#errContent").append('Failed to ' + action + ' task. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    function deleteTask(arrTaskToDel) {
        var success = true;
        var taskArrInJson = JSON.stringify(arrTaskToDel);
        $.ajax({
            url: '/pb/ajax/task',
            async: false,
            type: 'DELETE',
            data: taskArrInJson,
            contentType: "application/json; charset=utf-8",
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete tasks. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
                $("#dialog-form-delete").dialog( "close" );
            }
        });
        return success;
    };

    $( "#tDateFinished" ).change(function() {
        $( "#dialog-form-task" ).data("taskDFinished_changed", true);
    });

    $.validator.addMethod("greaterThan", function(value, element, params) {
        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) > new Date($(params).val());
        }
        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    });

    $("#taskForm").validate({
        rules: {
            taskTitle: {
                required: true,
                minlength: 2,
                maxlength: 100
            },
            taskDescription: {
                maxlength: 3000
            },
            taskProject:{
                required: {
                    depends: function(element) {
                        return $("#tProject").val() == '';
                    }
                }
            },
            taskDateStarted:{
                date: true,
                required: true
            },
            taskDeadline: {
                date: true,
                required: true,
                greaterThan: "#tDateStarted"
            },
            taskDateFinished: {
                date: true,
                greaterThan:{
                    param: "#tDateStarted",
                    depends: function(element) {
                        return $( "#dialog-form-task" ).data("taskDFinished_changed");
                    }
                }
            },
            taskStatusCombobox: {
                required: {
                    depends: function(element) {
                        return $("#tStatusCombobox").val() == '';
                    }
                }
            },
            takePriorityCombobox: {
                required: {
                    depends: function(element) {
                        return $("#tPriorityCombobox").val() == '';
                    }
                }
            },
            taskExCombobox: {
                required: {
                    depends: function(element) {
                        return $("#tExCombobox").val() == '';
                    }
                }
            }
        },
        messages: {
            taskTitle: {
                required: "This field is required",
                minlength: $.validator.format("Please enter at least {0} characters"),
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            taskDescription: {
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            taskProject:{
                required: "This field is required"
            },
            taskDateStarted: {
                required: "This field is required",
                date: "Please enter a valid date"
            },
            taskDeadline: {
                required: "This field is required",
                greaterThan: "Deadline must be greater then \"Date started\"",
                date: "Please enter a valid date"
            },
            taskDateFinished: {
                greaterThan: "Date finished must be greater then \"Date started\"",
                date: "Please enter a valid date"
            },
            taskStatusCombobox: {
                required: "This field is required"
            },
            takePriorityCombobox: {
                required: "This field is required"
            },
            taskExCombobox: {
                required: "This field is required"
            }
        }
    });

    function getDateFormatted(dateStr) {
        var d = new Date(dateStr);
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        if (day < 10) {
            day = "0" + day;
        }
        if (month < 10) {
            month = "0" + month;
        }
        var date = month + "-" + day + "-" + year;
        return date;
    };

    function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    };

    function getValidDate(initialDate){
        var arrDate = initialDate.toString().split('-');
        return new Date(arrDate[0], arrDate[1]-1, arrDate[2], 0, 0, 0, 0);
    };
});

//COLLEAGUES PAGE SECTION
$(document).ready(function(){
    var pageTemplate;
    var requestType = "";
    var userMap = {};

    $( "#dialog-form-user" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        open: function() {
            $(this).css('overflow', 'hidden');
            var user = $(this).data("user");
            if (user != null) {
                requestType = "PUT";

                $('#uName').val(user.name);
                $('#uSurname').val(user.surname);

                $('#uPosition').val(user.position);
                $('#uPhoneNum').val(user.phone);

                $('#uEmail').val(user.email);
                $('#uPassword').val(user.password);

                $('#uRoleCombobox').val(user.role.name);
                $('#uBirthDate').val(user.birthDate);
                $('#uAddress').val(user.address);

            }else{
                requestType = "POST";
                $('#uEmail, #uPassword').val('');
            }
        },
        close: function(){
            $(".inputDLP").val('');
            $( "label.error").hide();
        }
    });

    function getUserFromForm() {

        var user = $("#dialog-form-user").data("user") ? $("#dialog-form-user").data("user") : {};
        user.name = $('#uName').val();
        user.surname = $('#uSurname').val();

        user.email = $('#uEmail').val();
        user.password = $('#uPassword').val();

        user.position = $('#uPosition').val();
        user.phone = $('#uPhoneNum').val();

        user.role = {};
        user.role.id = $($('#uRoleCombobox').children(':selected')[0]).attr("id").substring("role_".length);

        user.birthDate = new Date($('#uBirthDate').val());
        user.address = $('#uAddress').val();

        return user;
    };


    function sendUser(user) {
        var failedAction = (requestType == "POST") ? 'add' : 'edit';
        if(userWithThisEmailExists(user)){
            $("#errContent").append('Impossible to ' + failedAction + ' user. User with this email already exists.');
            $("#dialog-form-error").dialog( "open" );
            return false;
        }

        var success = true;
        var userInJson = JSON.stringify(user);
        $.ajax({
            url: '/pb/ajax/user',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            type: requestType,
            data: userInJson,
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to ' + failedAction + ' user. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    function deleteUser(user) {
        var success = true;
        $.ajax({
            url: '/pb/ajax/user/' + user.id,
            async: false,
            type: 'DELETE',
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete user. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    function userWithThisEmailExists(user) {
        var success = false;
        $.ajax({
            url: '/pb/ajax/user/param?email=' + user.email,
            async: false,
            type: 'GET',
            success: function (data) {
                var existingUser = data;
                success = (existingUser.email == user.email && existingUser.id != user.id);
            },
            error: function (data) {
                if (data.responseText) {
                    var response = JSON.parse(data.responseText);
                    if (response.reason && response.reason == 'There is no user with such email') {
                        return;
                    }
                }
                $("#errContent").append(data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    $('#uBirthDate').datetimepicker({
        format:'unixtime',
        timepicker:false,
        format:'m-d-Y'
    });

    $.getJSON( "/pb/ajax/role", function( data ) {
        $.each( data, function( key, val ) {
            $("#uRoleCombobox").append( "<option id='role_" + val.id + "'>" + val.name + "</option>" );
        });
    });

    $( "#newUserButton" ).click(function() {
        $( "#dialog-form-user" ).data("pass_changed", true);
        $( "#dialog-form-user" ).data("user", null);
        $( "#dialog-form-user" ).dialog( "open" );
    });

    $("#saveUserButton").click( function(e){
        e.preventDefault();
        e.stopPropagation();

        if($("#userForm").valid()){
            var userToSend = getUserFromForm();
            if (sendUser(userToSend)) {
                $( "#dialog-form-user" ).dialog( "close" );
                reloadTabContent();
            }
        }
    });

    $( "#okDelButton" ).click(function(){
        if($(".colleagues-container").is(":visible")){
            var userToDel = $('#dialog-form-delete').data("userToDel");
            if(deleteUser(userToDel)){
                reloadTabContent();
                $("#dialog-form-delete").data("userToDel", null);
                $("#dialog-form-delete").dialog('close');
            }
        }
    });

    $("#userForm").validate({
        rules: {
            userNameField: {
                required: true,
                minlength: 2,
                maxlength: 50
            },
            userSurnameField: {
                required: true,
                minlength: 2,
                maxlength: 60
            },
            userEmailField: {
                required: true,
                email: true,
                maxlength: 60
            },
            userPasswordField: {
                required: {
                    depends: function(element) {
                        return $( "#dialog-form-user" ).data("pass_changed");
                    }
                },
                minlength: 5,
                maxlength: 100
            },
            userPositionField:{
                required: true,
                maxlength: 100
            },
            userPhoneNumField:{
                required: false,
                maxlength: 30,
                number: true
            },
            userBirthDateField: {
                required: false,
                date: true
            },
            userRoleCombobox:{
                required: {
                    depends: function(element) {
                        return $("#uRoleCombobox").val() == '';
                    }
                }
            },
            userAddressField:{
                required: false,
                maxlength: 100
            }
        },
        messages: {
            userNameField: {
                required: "This field is required",
                minlength: $.validator.format("Please enter at least {0} characters"),
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            userSurnameField: {
                required: "This field is required",
                minlength: $.validator.format("Please enter at least {0} characters"),
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            userEmailField: {
                required: "This field is required",
                email: "Please enter a valid email address"
            },
            userPasswordField: {
                required: "This field is required",
                minlength: $.validator.format("Please enter at least {0} characters"),
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            userPositionField:{
                required: "This field is required",
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            userPhoneNumField: {
                number: "Please enter a valid number",
                maxlength: $.validator.format("Please enter no more than {0} characters")
            },
            userBirthDateField: {
                date: 'Please enter a valid date.'
            },
            userRoleCombobox:{
                required: "This field is required"
            },
            userAddressField:{
                maxlength: $.validator.format("Address length should not exceed {0} characters.")
            }
        }
    });

    $( "#showHidePassword" ).change(function() {
        if($("#showHidePassword").is(':checked'))
            $("#uPassword").attr("type","text");
        else
            $("#uPassword").attr("type","password");
    });

    $( "#uPassword" ).change(function() {
        $( "#dialog-form-user" ).data("pass_changed", true);
    });

    $.ajax({
        async: false,
        url: '/mst/colleagues.mst',
        type: "GET",
        success: function (data) {
            pageTemplate = data;
        }
    });

    reloadTabContent();

    function reloadTabContent () {
        userMap = {};

        $.ajax({
            async: false,
            url: '/pb/ajax/user',
            dataType: "json",
            type: "GET",
            success: function (userList) {
                if (userList) {
                    $.each( userList, function(index, user) {
                        user.password = null; // hide the password hashes
                        userMap[user.id] = user;
                    });
                }

                var rendered = Mustache.render(pageTemplate, {"users": userList});
                $('#userList').empty();
                $('#userList').html(rendered);
                registerEventHandlers();
            }
        });

        $( ".userElem" ).accordion();

        function registerEventHandlers() {
            $( ".btnEditUser" ).click(function() {
                var userId = $(this).attr("id").substring("editUserBtn_".length);
                $( "#dialog-form-user" ).data("pass_changed", false);
                $( "#dialog-form-user" ).data("user", userMap[userId]);
                $( "#dialog-form-user" ).dialog( "open" );
            });
            $( ".btnDelUser" ).click(function() {
                var userId = $(this).attr("id").substring("delUserBtn_".length);
                $( "#dialog-form-delete" ).data("userToDel", userMap[userId]);
                $( "#dialog-form-delete" ).dialog( "open" );
            });
        }
    }
});

//NOTICE BOARD PAGE SECTION
$(document).ready(function(){

    var limit = 10;
    var pageTemplate;
    var requestType = "";

    $( "#dialog-form-announcement" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        open: function() {
            $(this).css('overflow', 'hidden');
            requestType = $(this).data("ann") ? "PUT" : "POST";
        },
        close: function(){
            $(".inputDLP.NA").val('');
            $("label.error").hide();
        }
    });

    $.ajax({
        async: false,
        url: '/mst/announcement.mst',
        type: "GET",
        success: function (data) {
            pageTemplate = data;
        }
    });

    getAnnList(limit);
    $(".annSection").selectable();

    function getAnnList(rowsLimit) {
        $.ajax({
            async: false,
            url: '/pb/ajax/announcement',
            dataType: "json",
            type: "GET",
            data: {
                rows : rowsLimit
            },
            success: function (annList) {
                $.each(annList, function(i, ann){
                    var dateTimeFormatted = getDateTimeFormatted(ann.dateCreation);
                    ann.dateCreation = dateTimeFormatted;
                });

                var rendered = Mustache.render(pageTemplate, {"announcements": annList});
                $('#annList').empty();
                $('#annList').html(rendered);
                $("#annList").selectable();
            }
        });
    };

    $("#loadMoreButton").click(function() {
        limit += 10;
        getAnnList(limit);
    });

    $( "#aFile" ).change(function() {
        $( "#dialog-form-announcement" ).data("file_changed", true);
    });

    $( "#newAnnButton" ).click( function() {
        $( "#dialog-form-announcement" ).data("ann", null);
        $( "#dialog-form-announcement" ).data("file_changed", false);
        $( "#dialog-form-announcement" ).dialog( "open" );
    });

    $( "#editAnnButton" ).click( function() {
        var selectedElems = getSelectedElems();
        if(selectedElems.length != 0){
            $( "#dialog-form-announcement" ).data("ann", getAnnObjFromList(selectedElems[0]));
            $( "#dialog-form-announcement" ).data("file_changed", false);
            $( "#dialog-form-announcement" ).dialog( "open" );
        }else{
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
    });

    $( "#deleteAnnButton" ).click(function() {
        var selectedElems = getSelectedElems();
        if(selectedElems.length != 0){
            var selectedElemsObj = [];

            $( selectedElems ).each(function( index ) {
                selectedElemsObj[index] = getAnnObjFromList(selectedElems[index]);
            });

            $( "#dialog-form-delete" ).data("annArr", selectedElemsObj);
            $( "#dialog-form-delete" ).dialog( "open" );
        }else{
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
    });

    $( "#saveAnnButton" ).click(function(e) {
        e.preventDefault();
        e.stopPropagation();

        if($("#annForm").valid()){
            var annToSend = getAnnFromForm();
            if (sendAnnData(annToSend)) {
                $( "#dialog-form-announcement" ).dialog( "close" );
                getAnnList(limit);
            }
        }
    });

    $( "#okDelButton" ).click(function(){
        if($(".announcements-container").is(":visible")){
            var annArrToDel = $('#dialog-form-delete').data("annArr");
            if(deleteAnn(annArrToDel)){
                $("#dialog-form-delete").data("annArr", null);
                $("#dialog-form-delete").dialog('close');
            }
        }
    });

    function sendAnnData(ann) {
        if(ann.file){
            var oldAnn = $( "#dialog-form-announcement" ).data("ann");
            if(oldAnn.file == null && $( "#dialog-form-announcement" ).data("file_changed") ){
                deleteFile(oldAnn.file.id);
            }
            ann.file.id = sendFileData(ann.file);
        }
        var success = true;
        var annInJson = JSON.stringify(ann);
        $.ajax({
            url: '/pb/ajax/announcement',
            contentType: "application/json; charset=utf-8",
            async: false,
            type: requestType,
            data: annInJson,
            dataType: "json",
            success: function () {
                getAnnList(limit);
            },
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to add/edit announcement. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    function getAnnObjFromList(selectedElem) {
        if($('#' + selectedElem).find(".annCreated")[0]){
            var dateArr = $('#' + selectedElem).find(".annCreated")[0].innerHTML.trim().split(" at");
            var userId = $($('#' + selectedElem).find(".annOwner")[0]).attr('id').substring("annOwner".length);
        }

        var ann = {};
        ann.id = $('#' + selectedElem).attr('id').substring("annElem".length);
        ann.title = $("#aTitle").val($('#' + selectedElem).find(".annTitle")[0].innerHTML);
        ann.desc = $("#aDescription").val($('#' + selectedElem).find(".annDesc")[0].innerHTML);
        ann.dateCreation  = new Date(dateArr[0] + dateArr[1]);
        ann.user = {};
        ann.user.id = userId;
        ann.file = {};
        ann.file.id = $($('#' + selectedElem).find(".annPicture")[0]).attr('id').substring("annFile".length);
        ann.file.name = $('#' + selectedElem).find(".annPicture")[0].innerHTML;

        return ann;
    }

    function getAnnFromForm() {
        var curAnn = $( "#dialog-form-announcement" ).data("ann") ? $( "#dialog-form-announcement" ).data("ann") : null;
        var ann = {};
        ann.user = {};
        ann.file = {};

        if(curAnn){
            ann.id = curAnn.id;
            ann.dateCreation = new Date(curAnn.dateCreation);
            ann.user.id = curAnn.user.id;
            if($( "#dialog-form-announcement" ).data("file_changed") && $('#aFile').val() != ''){
                ann.file.name = $('#aFile').val();
            }else if($('#aFile').val() == ''){
                ann.file = null;
            }else{
                ann.file.id = curAnn.file.id;
                ann.file.name = curAnn.file.name;
            }
        }else{
            ann.dateCreation = new Date();
            ann.user.id = getCookie("userId");
            if($( "#dialog-form-announcement" ).data("file_changed") && $('#aFile').val() != ''){
                ann.file.name = $('#aFile').val();
            }else{
                ann.file = null;
            }
        }
        ann.name = $('#aTitle').val();
        ann.description = $('#aDescription').val();

        return ann;
    };

    function sendFileData(file) {
        var myFormData = new FormData();
        myFormData.append('file', $('#aFile').prop('files')[0]);
        var newFileId;
        $.ajax({
            url: '/pb/ajax/file/upload',
            processData: false,
            type: 'POST',
            data: myFormData,
            contentType: false,
            async: false,
            type: 'POST',
            dataType: "json",
            success: function (data) {
                newFileId = data.id;
            },
            error: function (data) {
                $("#errContent").append('Failed to add/edit file. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return newFileId;
    };

    function deleteAnn(arrAnnToDel) {
        $( arrAnnToDel ).each(function( index ) {
            if(arrAnnToDel.file){
                deleteFile(arrAnnToDel[index].file.id );
            }
        });

        var success = true;
        var annArrIdInJson = JSON.stringify(getSelectedElemsId());

        $.ajax({
            url: '/pb/ajax/announcement',
            async: false,
            type: 'DELETE',
            contentType: "application/json; charset=utf-8",
            data: annArrIdInJson,
            success: function () {
                getAnnList(limit);
            },
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete announcement. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
                $("#dialog-form-delete").dialog( "close" );
            }
        });
        return success;
    };

    function deleteFile(fileIdToDel) {
        var success = true;
        $.ajax({
            url: '/pb/ajax/file/' + fileIdToDel,
            async: false,
            type: 'DELETE',
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete file. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
                $("#dialog-form-delete").dialog( "close" );
            }
        });
        return success;
    }

    $.validator.addMethod('filesize', function(value, element, param) {
        return this.optional(element) || (element.files[0].size/1024/1024 <= param)
    });

    $("#annForm").validate({
        rules: {
            annTitle: {
                required: true,
                minlength: 5,
                maxlength: 70
            },
            annFile: {
                required: false,
                filesize: 15
            }
        },
        messages: {
            annTitle: {
                required: "Announcement title is required",
                minlength: $.validator.format("At least {0} characters required in \"Title\" field"),
                maxlength: $.validator.format("Title length should not exceed {0} characters")
            },
            annFile: {
                filesize: "File must be less than 15MB"
            }
        }});

    function getSelectedElems(){
        var selectedElems = [];
        $('.annSection .ui-widget-content.ui-selected').each(function(index) {
            selectedElems[index] =  $(this).attr('id');
        });
        return selectedElems;
    };

    function getSelectedElemsId(){
        var selectedElemsId = [];
        $('.annSection .ui-widget-content.ui-selected').each(function(index) {
            selectedElemsId[index] = $(this).attr('id').substring("annElem".length);
        });
        return selectedElemsId;
    };

    function getDateTimeFormatted(dateStr) {
        var d = new Date(dateStr);
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        if (day < 10) {
            day = "0" + day;
        }
        if (month < 10) {
            month = "0" + month;
        }
        var hh = d.getHours();
        var mm = d.getMinutes();
        var ss = d.getSeconds();
        if (hh < 10) {hh = "0"+hh;}
        if (mm < 10) {mm = "0"+mm;}
        if (ss < 10) {ss = "0"+ss;}
        var time = hh+":"+mm+":"+ss;
        var date = month + "/" + day + "/" + year + " at " + time;
        return date;
    };

    function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    };
});

//COMPANY FILES PAGE SECTION
$(document).ready(function(){
    var pageTemplate;
    var pageTemplate_files;
    var requestType = "";
    var sectionMap = {};
    var filesMap = {};

    $.ajax({
        async: false,
        url: '/mst/company_files_section.mst',
        type: "GET",
        success: function (data) {
            pageTemplate = data;
        }
    });

    $.ajax({
        async: false,
        url: '/mst/files.mst',
        type: "GET",
        success: function (data) {
            pageTemplate_files = data;
        }
    });

    function getDialogFileContent(sectionId){
        $.ajax({
            async: false,
            url: '/pb/ajax/companyfiles/list/' + sectionId,
            type: "GET",
            success: function (filesList) {
                if (filesList) {
                    $.each( filesList, function(index, file) {
                        filesMap[file.id] = file;
                    });
                    var rendered = Mustache.render(pageTemplate_files, {"files": filesList});
                    $('.files-table').html(rendered);

                    var table = $('#filesList').DataTable({
                        "scrollY": '60%',
                        "bFilter": false,
                        "scrollX": false,
                        "autoWidth": false,
                        "bAutoWidth": false,
                        "aoColumns": [
                            { "sWidth": "20%" },
                            { "sWidth": "80%" }
                        ]
                    });
                }else{
                    $('.files-box').html("No files to display in this section");
                }
            },
            error: function (data) {
                $("#errContent").append(data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });

        $('#selectAll').click( function () {
            var checkAll = this.checked;
            $('.check.filerow').each(function() {
                var checkedRow = this.parentElement.parentElement;
                this.checked = checkAll;
                $(checkedRow).toggleClass('selected', checkAll);
            });
        });

        $(".check.filerow").change(function() {
            $(this.parentElement.parentElement).toggleClass('selected');
        });
    }

    $( "#dialog-form-section" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        open: function() {
            $(this).css('overflow', 'hidden');
            var section = $(this).data("section");
            if (section != null) {
                requestType = "PUT";
                $('#sName').val(section.name);
            }else{
                requestType = "POST";
                $('#sName').val('');
            }
        },
        close: function(){
            $(".inputDLP").val('');
            $( "label.error").hide();
        }
    });

    $( "#dialog-form-section-file" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        close: function(){
            $(".files-table").empty();
            $( "#fileForm" ).hide();
            $( "#sFile" ).val('');
            $("#dialog-form-section-file").data( "sectionId",null );
        }
    });

    function getSectionFromForm() {
        var section = $("#dialog-form-section").data("section") ? $("#dialog-form-section").data("section") : {};
        section.name = $('#sName').val();
        return section;
    };

    function sendSection(section) {
        var success = true;
        var sectionInJson = JSON.stringify(section);
        $.ajax({
            url: '/pb/ajax/companyfiles',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            type: requestType,
            data: sectionInJson,
            error: function (data) {
                success = false;
                var failedAction = (requestType == "POST") ? 'add' : 'edit';
                $("#errContent").append('Failed to ' + failedAction + ' section. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    function deleteSection(section) {
        var success = true;
        $.ajax({
            url: '/pb/ajax/companyfiles/' + section.id,
            async: false,
            type: 'DELETE',
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete section. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return success;
    };

    $( "#newSectionButton" ).click(function() {
        $( "#dialog-form-section" ).data("section", null);
        $( "#dialog-form-section" ).dialog( "open" );
    });

    $( "#saveSectionButton" ).click( function(e){
        preventDefaultBehavior(e);

        if($("#sectionForm").valid()){
            var sectionToSend = getSectionFromForm();
            if (sendSection(sectionToSend)) {
                $( "#dialog-form-section" ).dialog( "close" );
                reloadTabContent();
            }
        }
    });

    $( "#okDelButton" ).click(function(){
        if($(".companyInfo-container").is(":visible")){
            if($('#dialog-form-delete').data("sectionToDel")){
                var sectionToDel = $('#dialog-form-delete').data("sectionToDel");
                if(deleteSection(sectionToDel)){
                    reloadTabContent();
                    $("#dialog-form-delete").data("sectionToDel", null);
                    $("#dialog-form-delete").dialog('close');
                }
            }
            if($('#dialog-form-delete').data("fileArrToDel")){
                var filesToDel = $('#dialog-form-delete').data("fileArrToDel");
                if(deleteFiles(filesToDel)){
                    $('.files-table').empty();
                    getDialogFileContent($("#dialog-form-section-file").data( "sectionId" ));
                    $("#dialog-form-delete").data("fileArrToDel", null);
                    $("#dialog-form-delete").dialog('close');
                }
            }
        }
    });

    $( "#addFileButton" ).click(function() {
        $( "#fileForm" ).toggle("slow");
    });

    $( "#delFileButton" ).click(function() {
        var selectedElems = getSelectedFiles();
        if(selectedElems.length != 0){
            $( "#dialog-form-delete" ).data("fileArrToDel", selectedElems);
            $( "#dialog-form-delete" ).dialog( "open" );
        }else{
            $("#errContent").append('No elements have been chosen. Please try again.');
            $("#dialog-form-error").dialog( "open" );
        }
    });

    $( "#saveFileButton" ).click(function(e) {
        preventDefaultBehavior(e);

        if($("#fileForm").valid()){
            var fileToSend = getFileFromForm();
            if (sendFileData(fileToSend)) {
                $('.files-table').empty();
                getDialogFileContent($("#dialog-form-section-file").data( "sectionId" ));
            }
        }
    });

    function getFileFromForm() {
        var file = {};
        file.name = $('#sFile').val();
        return file;
    };

    function getSelectedFiles () {
        var objArr = [];
        var oTable = $('#filesList').DataTable();

        var selectedItems;
        if (oTable.rows('.selected')) {
            selectedItems = oTable.rows('.selected').data();
            $.each (selectedItems, function (index, item) {
                objArr [objArr.length] = $(item[1]).attr('id').substring("file".length);
            })
        }
        return objArr;
    }

    function sendFileData(file) {
        var myFormData = new FormData();
        myFormData.append('file', $('#sFile').prop('files')[0]);
        var sectionId = $("#dialog-form-section-file").data( "sectionId" );
        var newFileId = null;
        $.ajax({
            url: '/pb/ajax/companyfiles/list/' + sectionId,
            processData: false,
            type: 'POST',
            data: myFormData,
            contentType: false,
            async: false,
            success: function (data) {
                newFileId = data.id;
            },
            error: function (data) {
                $("#errContent").append('Failed to add/edit file. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
            }
        });
        return newFileId;
    };

    function deleteFiles(fileIdToDel) {
        var success = true;
        var fileArrIdInJson = JSON.stringify(fileIdToDel);
        $.ajax({
            url: '/pb/ajax/file',
            async: false,
            type: 'DELETE',
            data: fileArrIdInJson,
            contentType: "application/json; charset=utf-8",
            error: function (data) {
                success = false;
                $("#errContent").append('Failed to delete file. ' + data.statusText);
                $("#dialog-form-error").dialog( "open" );
                $("#dialog-form-delete").dialog( "close" );
            }
        });
        return success;
    }

    $.validator.addMethod('filesize', function(value, element, param) {
        return this.optional(element) || (element.files[0].size/1024/1024 <= param)
    });

    $("#sectionForm").validate({
        rules: {
            sectionName:{
                required: true,
                minlength: 2,
                maxlength: 25
            }
        },
        messages: {
            sectionName: {
                required: "This field is required",
                minlength: $.validator.format("Please enter at least {0} characters"),
                maxlength: $.validator.format("Please enter no more than {0} characters")
            }
        }
    });

    $("#fileForm").validate({
        rules: {
            newFile: {
                required: true,
                filesize: 15
            }
        },
        messages: {
            newFile: {
                required: "This field is required",
                filesize: "File must be less than 15MB"
            }
        }
    });

    reloadTabContent();

    function reloadTabContent () {
        sectionMap = {};

        $.ajax({
            async: false,
            url: '/pb/ajax/companyfiles',
            dataType: "json",
            type: "GET",
            success: function (sectionList) {
                if (sectionList) {
                    $.each( sectionList, function(index, section) {
                        sectionMap[section.id] = section;
                    });
                }
                var rendered = Mustache.render(pageTemplate, {"sections": sectionList});
                $('#sectionList').empty();
                $('#sectionList').html(rendered);
                registerEventHandlers();
            }
        });
    };

    function registerEventHandlers() {
        $(".sectionImg.sectionFolder").click( function () {
            var sectionId = $($(this).parent()).attr('id').substring("sectionElem_".length);
            $("#dialog-form-section-file").data( "sectionId",sectionId );
            getDialogFileContent(sectionId);
            $("#dialog-form-section-file").dialog( "open" );
        });

        $( ".btnEditSection" ).click(function() {
            var sectionId = $(this).attr("id").substring("editSectionBtn_".length);
            $( "#dialog-form-section" ).data("section", sectionMap[sectionId]);
            $( "#dialog-form-section" ).dialog( "open" );
        });
        $( ".btnDelSection" ).click(function() {
            var sectionId = $(this).attr("id").substring("delSectionBtn_".length);
            $( "#dialog-form-delete" ).data("sectionToDel", sectionMap[sectionId]);
            $( "#dialog-form-delete" ).dialog( "open" );
        });
    };

    function preventDefaultBehavior(e) {
        e.preventDefault();
        e.stopPropagation();
    };
});
