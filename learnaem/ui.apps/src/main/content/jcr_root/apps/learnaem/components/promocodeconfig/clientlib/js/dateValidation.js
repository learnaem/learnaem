(function(document, $, ns) {
    "use strict";
    $(document).on("click", ".cq-dialog-submit", function(e) {
        e.stopPropagation();

        var $form = $(this).closest("form.foundation-form"),
		$inputs = $form.find("[name$='./startDate']"),
		$input = null,
        startdate,
		isError = false,
		patterns = {
			datePattern: /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/([0-9]){2}$/
		};

		$inputs.each(function(index, input) {

			$input = $(input);
			tile = $input.val();
            var startDateV = "./startDate";
            var endDateV = "./endDate";
         var startdate= $("input[name='"+startDateV+"']").val();
        var endDate= $("input[name='"+endDateV+"']").val();
            console.log(patterns.datePattern.test(startdate));
            console.log(patterns.datePattern.test(endDate));
			if (!patterns.datePattern.test(startdate) || !patterns.datePattern.test(endDate)) {
				 e.preventDefault();
				isError = true;
				$input.css("border", "2px solid #FF0000");
				ns.ui.helpers.prompt({
					title: Granite.I18n.get("Invalid Input"),
					message: "Please correct Promotion Start Date In Format (mm/dd/yy).",
					actions: [{
						id: "CANCEL",
						text: "OK",
						className: "coral-Button"
					}],
					callback: function(actionId) {
						if (actionId === "CANCEL") {}
					}
				});
			} else if (!patterns.datePattern.test(endDate)) {
				 e.preventDefault();
				isError = true;
				$input.css("border", "2px solid #FF0000");
				ns.ui.helpers.prompt({
					title: Granite.I18n.get("Invalid Input"),
					message: "Please correct Promotion End Date In Format (mm/dd/yy).",
					actions: [{
						id: "CANCEL",
						text: "OK",
						className: "coral-Button"
					}],
					callback: function(actionId) {
						if (actionId === "CANCEL") {}
					}
				});
			}
            else {
				$input.css("border", "");
			}
		});
        if (!isError) {
            $form.submit();
        }
    });

})(document, Granite.$, Granite.author);
