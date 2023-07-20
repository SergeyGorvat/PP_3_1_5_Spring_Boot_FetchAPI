"use strict";

const url ="http://localhost:3333/admin/";
$(async function () {
    await getAllUsers();
});

const table = $('#listOfUsers');

async function getAllUsers() {
    table.empty();
    fetch(url)
        .then(result => result.json())
        .then(data => {
            data.forEach(user => {
                let tableWithUsers = `$(
                    <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => " " + role.roleTitle.slice(5))}</td>
                    <td>
                        <button id="buttonEdit" type="button" class="buttonEdit btn btn-primary" data-toggle="modal" 
                        data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
                    </td>
                    <td>
                        <button id="buttonDelete" type="button" class="buttonDelete btn btn-danger" data-toggle="modal" 
                        data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
                    </td>
                    </tr>
                )`;
                table.append(tableWithUsers);
            });
        })
}

async function getUserById(id) {
    let urlForOneUser = url + id;
    return await (await fetch(urlForOneUser)).json();
}

async function getAllRoles(selectRole) {
    fetch("http://localhost:8080/admin/roles")
        .then(result => result.json())
        .then(data => {
            let temp = "";
            data.forEach(role =>
            {
                temp += `<option value="${role.id}">${role.roleTitle.slice(5)}</option>`;
            });
            selectRole.append(temp);
        });
}

function getSelectedRole(selectTag) {
    let roles = [];
    for (let i = 0; i < selectTag.options.length; i++) {
        if (selectTag.options[i].selected) roles.push({
            id: selectTag.options[i].value,
            roleTitle: "ROLE_" + selectTag.options[i].text
        });
    }
    return roles;
}

//edit user

const modalEdit = new bootstrap.Modal(document.querySelector('#edit'));
const editUserForm = document.forms["editUserForm"];

const idEdit = document.getElementById('IDEditUserForm');
const usernameEdit = document.getElementById('usernameEditUserForm');
const ageEdit = document.getElementById('ageEditUserForm');
const emailEdit = document.getElementById('emailEditUserForm');
const passwordEdit = document.getElementById('passwordEditUserForm');
const rolesEdit = document.getElementById('rolesEditUserForm');
const selectRoleInEdit = $('#rolesEditUserForm');

const on  = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e);
        }
    })
}

let rowId = 0;
on(document, 'click', '.buttonEdit', e => {
        const row = e.target.parentNode.parentNode;
        rowId = row.firstElementChild.innerHTML
        getUserById(rowId).then(data => {
            idEdit.value = data.id;
            usernameEdit.value = data.username;
            ageEdit.value = data.age;
            emailEdit.value = data.email;
            passwordEdit.value = '';
            getAllRoles(selectRoleInEdit);
            modalEdit.show();
        });
    }
);

editUserForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(url, {
        method: "PATCH",
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(
            {
                id: editUserForm.ID.value,
                username: usernameEdit.value,
                age: ageEdit.value,
                email: emailEdit.value,
                password: passwordEdit.value,
                roles: getSelectedRole(rolesEdit)
            }
        )}).then(() => {
        $('#closeButtonEditForm').click();
        getAllUsers()})
        .catch(error => console.log(error));
});

//delete user

const modalDelete = new bootstrap.Modal(document.querySelector('#delete'));
const deleteUserForm = document.forms["deleteUserForm"];

const idDelete= document.getElementById('IDdeleteUserForm');
const usernameDelete = document.getElementById('usernamedeleteUserForm');
const ageDelete = document.getElementById('ageDeleteUserForm');
const emailDelete = document.getElementById('emailDeleteUserForm');
const rolesDelete = document.getElementById('rolesDeleteUserForm');
const selectRoleInDelete = $('#rolesDeleteUserForm');

let deleteRowId = 0;

on(document, 'click', '.buttonDelete', e => {
    const row = e.target.parentNode.parentNode;
    deleteRowId = row.firstElementChild.innerHTML
    getUserById(deleteRowId).then(data => {
        idDelete.value = data.id;
        usernameDelete.value = data.username;
        ageDelete.value = data.age;
        emailDelete.value = data.email;
        getAllRoles(selectRoleInDelete);
        modalDelete.show();
    });}
);

deleteUserForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(url + deleteRowId, {
        method: "DELETE",
        headers: {
            'content-type': 'application/json'
        },
    }).then(() => {
        $('#closeButtonDeleteForm').click();
        getAllUsers()})
        .catch(error => console.log(error));
});


// create user

const createTab = document.getElementById('create');

const usernameAdd = document.getElementById('newUsername');
const ageAdd = document.getElementById('newAge');
const emailAdd = document.getElementById('newEmail');
const passwordAdd = document.getElementById('newPassword');
const rolesAdd = document.getElementById('chooseRole');
const selectRoleInCreate = $('#chooseRole');
getAllRoles(selectRoleInCreate);

createTab.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(url, {
        method: "POST",
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(
            {
                username: usernameAdd.value,
                age: ageAdd.value,
                email: emailAdd.value,
                password: passwordAdd.value,
                roles: getSelectedRole(rolesAdd)
            }
        )
    }).then(() => {
        createTab.reset();
        $('#allUsersTab').click();
        getAllUsers()})
        .catch(error => console.log(error));
});w