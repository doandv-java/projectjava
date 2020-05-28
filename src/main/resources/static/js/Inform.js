var informmes = document.createElement('div');
informmes.classList += 'notification';
informmes.id = 'alert';
var thespan = document.createElement('span');
thespan.classList += 'span_alert';

informmes.appendChild(thespan);

var style_alert = "font-family:'serif'; " +
    "position: fixed; z-index: " +
    "10000;height:50px;" +
    " bottom: 15%; right: 1%;" +
    " width:fit-content;" +
    " font-size: 1em;" +
    " padding: 1em 1em; " +
    "border-radius: 8px;";

informmes.setAttribute("style", style_alert);
var style = "display: none;  z-index: 10000; padding: 50px; position: fixed; top: 40% ; transform: translateX(-50%); left: 50%; width: 500px;";

$(document).ready(function () {
    var main = document.getElementById('container');
    main.appendChild(informmes);
});

var log = new Log();

function Log() {
    /**
    *Make a notification that allows you to use keyboard shortcuts to perform close form (enter, esc);
    *Call: log.alert('Select many records', 'Alert', function () {	console.log('Say something');}.bind(this));//must have the first parameter
    */
    this.alert = function (content, title, callback) {
        if (!title)//Default
        {
            title = 'Notification';
        }
        if (!!callback) {
            $.alert({ title: title, content: content, closeIcon: true, buttons: { confirm: { text: 'OK', keys: ['enter', 'esc'], action: callback } } });
        } else {
            $.alert({ title: title, content: content, closeIcon: true, buttons: { confirm: { text: 'OK', keys: ['enter', 'esc'] } } });
        }
    };
    /**
    *Execute giving a question with a opt yes/no; default Y, enter:  yes; N, esc:  no
    *Call: log.confirm('Delete record','Chú ý',function(){console.log('Execute Delete');}.bind(this),function(){console.log('Can not execute delete');}.bind(this)); //must have the first parameter
    */
    this.confirm = function (content, title, yescallback, nocallback) {
        if (!title) {
            title = 'Confirm';
        }
        $.confirm({
            title: title, content: content,
            buttons: {
                yes: {
                    keys: ['Y', 'enter'],
                    action: yescallback
                },
                no: {
                    keys: ['N', 'esc'],
                    action: nocallback
                }
            }
        });
    };
    this.show = function (message, result) {
        thespan.innerText = message;
        informmes.style.display = 'block';
        if (result) {
            informmes.style.backgroundColor = 'rgba(7, 138, 5, 0.5)';
            thespan.style.color = '#e5fbe0';
            //alert.classList += 'alert-success';

        }
        else {
            informmes.style.backgroundColor = 'rgba(181, 72, 72, 0.5)';
            thespan.style.color = '#e5fbe0';
            // alert.classList += 'alert-danger';
        }

        setTimeout(function () {
            informmes.style.display = 'none';
        }, 2500);
    };
}
