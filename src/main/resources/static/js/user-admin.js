function addRow(idCol, nameCol, emailCol, editBtn, header) {

    let tagName = header ? 'th' : 'td';
    let tagBtn = header ? 'Action' : `
        <button class="btn btn-primary" onclick="findOne(${idCol})">
            Edit
        </button>
    `;
    let template = `
        <tr>
            <${tagName}>${idCol}</${tagName}>
            <${tagName}>${nameCol}</${tagName}>
            <${tagName}>${emailCol}</${tagName}>
            <${tagName}>${tagBtn}</${tagName}>
        </tr>
    `;

    $('#user-data > table ' + (header ? 'thead' : 'tbody')).append(template);
}

function findAll() {
    $.get('/resources/users').done((data) => {
        $('#user-data').empty();
        $('#user-data').append(`
            <table style="width: 100%;" class="table">
                <thead></thead>
                <tbody></tbody>
            </table>
        `);
        addRow('#', 'Name', 'Email', true, true);
        data.forEach((item) => {
            addRow(item.id, item.name, item.email);
        });
    });
}

function findOne(id) {
    $.get('/resources/users/' + id).done((data) => {
        loadData(data);
    });
}

function loadUserRoles(roles, data) {
    $('#user-list').empty();
    if(data && data.roles) {
        data.roles.forEach((item) => {

        });
    }
}

function loadData(data) {
    $('#id').val(data.id);
    $('#name').val(data.name);
    $('#email').val(data.email);
    $('#password').val(data.password);
    if(data.bornDate)
        $('#bornDate').val(new Date(data.bornDate).toISOString().split('T')[0]);
    else
        $('#bornDate').val(undefined);
    $.get('/resources/roles').done((roles) => {
        loadUserRoles(roles, data);
    });
}

$('#save-btn').click(() => {
    var data = {};
    $("#user-form").serializeArray().map(function(x){data[x.name] = x.value;});
    data.bornDate = new Date(data.bornDate).getTime();
    $.ajax({
        method: "POST",
        url: '/resources/users',
        data: JSON.stringify(data),
        contentType: 'application/json'
    }).done(function() {
        findAll();
        loadData({});
    });
});

findAll();