function addRow(idCol, nameCol, editBtn, header) {

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
            <${tagName}>${tagBtn}</${tagName}>
        </tr>
    `;

    $('#role-data > table ' + (header ? 'thead' : 'tbody')).append(template);
}

function findAll() {
    $.get('/resources/roles').done((data) => {
        $('#role-data').empty();
        $('#role-data').append(`
            <table style="width: 100%;" class="table">
                <thead></thead>
                <tbody></tbody>
            </table>
        `);
        addRow('#', 'Name', true, true);
        data.forEach((item) => {
            addRow(item.id, item.name);
        });
    });
}

function findOne(id) {
    $.get('/resources/roles/' + id).done((data) => {
        loadData(data);
    });
}

function loadData(data) {
    $('#id').val(data.id);
    $('#name').val(data.name);
}

$('#save-btn').click(() => {
    var data = {};
    $("#role-form").serializeArray().map(function(x){data[x.name] = x.value;});
    $.ajax({
        method: "POST",
        url: '/resources/roles',
        data: JSON.stringify(data),
        contentType: 'application/json'
    }).done(function() {
        findAll();
        loadData({});
    });
});

$('#new-btn').click(() => {
    loadData({});
});

findAll();