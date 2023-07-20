$(async function () {
    await getCurrentUser();
});

async function getCurrentUser() {

    fetch("http://localhost:8080/user/auth_data")
        .then(result => result.json())
        .then(user => {
            $('#usernameHeader').append(user.email);
            let roles = user.roles.map(role => " " + role.roleTitle.slice(5));
            $('#rolesHeader').append(roles);

            let tableWithUserInfo = `$(
                    <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                    </tr>)`;
            $('#currentUserInfo').append(tableWithUserInfo);
        });
}