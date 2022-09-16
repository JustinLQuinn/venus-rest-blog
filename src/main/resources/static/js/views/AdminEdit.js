
let users;

export default function PostIndex(props) {
    const postsHTML = generateUsersHTML(props.users);
    // save this for loading edits later
    users = props.users;

    return `
        <header>
            <h1>Admin Page</h1>
        </header>
        <main>
              <h3>Lists of Users</h3>
            <div>
                ${postsHTML}   
            </div>  
            <div>         
                <h3>Edit a User</h3>
                <form>
                    <label>User: (username)</label><br>
                    <label for="changEmail">Email</label><br>
                    <input id="changEmail" name="email" type="email" placeholder="New Email">
                    <br>
                    <label for="changPassword">Password</label><br>
                    <input id="changPassword" name="password" type="email" placeholder="New Password">
                    <br>
                    <label for="changRole">Role</label><br>
                    <input id="changRole" name="role" type="email" placeholder="New Role">
                    <br>
                    <button data-id="0" id="savePost" name="savePost" class="button btn-primary">Save User</button>
                </form>
            </div>
        </main>
    `;
}

function generateUsersHTML(users) {
    let postsHTML = `
        <table class="table">
        <thead>
        <tr>
            <th scope="col">Username</th>
            <th scope="col">Start Date</th>
            <th scope="col">Email</th>
            <th scope="col">Password</th>
            <th scope="col">Role</th>
        </tr>
        </thead>
        <tbody>
    `;
    for (let i = 0; i < users.length; i++) {
        const post = users[i];
        postsHTML += `<tr>
            <td>${post.username}</td>
            <td>${post.createdAt}</td>
            <td>${post.email}</td>
            <td>${post.password}</td>
            <td>${post.role}</td>
            <td><button data-id=${post.id} class="button btn-primary editPost">Edit</button></td>
            <td><button data-id=${post.id} class="button btn-danger deletePost">Delete</button></td>
            </tr>`;
    }
    postsHTML += `</tbody></table>`;
    return postsHTML;
}




export function userSetup() {
    // setupSaveHandler();
    // setupEditHandlers();
    // setupDeleteHandlers();
}

// function setupEditHandlers() {
//     // target all delete buttons
//     const editButtons = document.querySelectorAll(".editPost");
//     // add click handler to all delete buttons
//     for (let i = 0; i < editButtons.length; i++) {
//         editButtons[i].addEventListener("click", function(event) {
//
//             // get the post id of the delete button
//             const postId = parseInt(this.getAttribute("data-id"));
//
//             loadPostIntoForm(postId);
//         });
//     }
// }
//
// function loadPostIntoForm(postId) {
//     // go find the post in the posts data that matches postId
//     const post = fetchPostById(postId);
//     if(!post) {
//         console.log("did not find post for id " + postId);
//         return;
//     }
//
//     // load the post data into the form
//     const titleField = document.querySelector("#title");
//     const contentField = document.querySelector("#content");
//     titleField.value = post.title;
//     contentField.value = post.content;
//
//     const saveButton = document.querySelector("#savePost");
//     saveButton.setAttribute("data-id", postId);
// }
//
// function fetchPostById(postId) {
//     for (let i = 0; i < posts.length; i++) {
//         if(posts[i].id === postId) {
//             return posts[i];
//         }
//
//     }
//     // didn't find it so return something falsy
//     return false;
// }
//
//
//
//
//
//
// function setupDeleteHandlers() {
//     // target all delete buttons
//     const deleteButtons = document.querySelectorAll(".deletePost");
//     // add click handler to all delete buttons
//     for (let i = 0; i < deleteButtons.length; i++) {
//         deleteButtons[i].addEventListener("click", function(event) {
//
//             // get the post id of the delete button
//             const postId = this.getAttribute("data-id");
//
//             deletePost(postId);
//         });
//     }
// }
//
// function deletePost(postId) {
//     const request = {
//         method: "DELETE",
//         headers: {"Content-Type": "application/json"},
//     }
//     const url = POST_API_BASE_URL + `/${postId}`;
//     fetch(url, request)
//         .then(function(response) {
//             if(response.status !== 200) {
//                 console.log("fetch returned bad status code: " + response.status);
//                 console.log(response.statusText);
//                 return;
//             }
//             CreateView("/posts");
//         })
// }
//
//
//
//
//
//
//
// function setupSaveHandler() {
//     const saveButton = document.querySelector("#savePost");
//     saveButton.addEventListener("click", function(event) {
//         const postId = parseInt(this.getAttribute("data-id"));
//         savePost(postId);
//     });
// }
//
// function savePost(postId) {
//     // get the title and content for the new/updated post
//     const titleField = document.querySelector("#title");
//     const contentField = document.querySelector("#content");
//
//     // make the new/updated post object
//     const post = {
//         title: titleField.value,
//         content: contentField.value
//     }
//
//     // make the request
//     const request = {
//         method: "POST",
//         headers: {"Content-Type": "application/json"},
//         body: JSON.stringify(users)
//     }
//     let url = USER_API_BASE_URL;
//
//     // if we are updating a post, change the request and the url
//     if(postId > 0) {
//         request.method = "PUT";
//         url += `/${postId}`;
//     }
//
//     fetch(url, request)
//         .then(function(response) {
//             if(response.status !== 200) {
//                 console.log("fetch returned bad status code: " + response.status);
//                 console.log(response.statusText);
//                 return;
//             }
//             CreateView("/posts");
//         })
// }