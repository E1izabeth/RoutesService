/* <![CDATA[ */
function prepareWebMethod(methodValue, webHrefValue, hrefValue) {
    var form = document.getElementById('webMethodForm');
    // form.method = methodValue;
    form.action = webHrefValue;

    document.getElementById('requestMethod').value = methodValue;
    document.getElementById('requestTarget').value = hrefValue;

    form.style.visibility = 'visible';
}
/* ]]> */
