import CreateView from "../createView.js";
import {getHeaders, getUser, getUserRole, isLoggedIn} from "../auth.js";

let posts;
let loggedInUser;

export default function PostIndex(props) {
    //refresh current logged in User
    loggedInUser = getUser();
    const postsHTML = generatePostsHTML(props.posts);
    // save this for loading edits later
    posts = props.posts;

    return `
        <header>
            <h1>Posts Page</h1>
        </header>
        <main>
              <h3>Lists of posts</h3>
            <div>
                ${postsHTML}   
            </div>`;
    if (isLoggedIn()) {
        return `
            <h3>Add a post</h3>
            <form>
                <div>
                    <label for="title">Title</label><br>
                    <input id="title" name="title" class="form-control" type="text" placeholder="Enter title">
                    <div class="invalid-feedback">
                        Title cannot be blank.
                    </div>
                    <div class="valid-feedback">
                        Your title is ok!
                    </div>
                </div>
                
                <div>
                    <label for="content">Content</label><br>
                    <textarea id="content" class="form-control" name="content" rows="10" cols="50" placeholder="Enter content"></textarea>
                    <div class="invalid-feedback">
                        Content cannot be blank.
                    </div>
                    <div class="valid-feedback">
                        Content is ok!
                    </div>
                </div>
                   <button data-id="0" id="savePost" name="savePost" class="button btn-primary">Save Post</button>        
            </form>
            
        </main>
    `;
    }
}


    function generatePostsHTML(posts) {
        let postsHTML = `
        <table class="table">
        <thead>
        <tr>
            <th scope="col">Title</th>
            <th scope="col">Content</th>
            <th scope="col">Author</th>
            <th scope="col">Categories</th>
        </tr>
        </thead>
        <tbody>
    `;
        for (let i = 0; i < posts.length; i++) {
            const post = posts[i];
            let categories = '';
            if (post.categories) {
                for (let j = 0; j < post.categories.length; j++) {
                    if (categories !== "") {
                        categories += ", ";
                    }
                    categories += post.categories[j].name;
                }
            }
            let authorName = "";
            if (post.author) {
                authorName = post.author.username.toString();
            }
            postsHTML += `<tr>
            <td>${post.title}</td>
            <td>${post.content}</td>
            <td>${authorName}</td>
            <td>${categories}</td>`;
            if (isLoggedIn()) {
                postsHTML += `
            <td>
            <button data-id=${post.id} class="button btn-primary editPost">Edit</button>
            </td>
            <td>
            <button data-id=${post.id} class="button btn-danger deletePost">Delete</button>
            </td>`;
            }

            postsHTML += `</tr></tbody></table>`;
            return postsHTML;
        }
    }

    export function postSetup() {
        setupSaveHandler();
        setupEditHandlers();
        setupDeleteHandlers();
        setupValidationHandlers();
        validateFields();
    }

    function setupValidationHandlers() {
        let input = document.querySelector("#title");
        input.addEventListener("keyup", validateFields);
        input = document.querySelector("#content");
        input.addEventListener("keyup", validateFields);
    }

    function validateFields() {
        let isValid = true;
        let input = document.querySelector("#title");
        if (input.value.trim().length < 1) {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            isValid = false;
        } else {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
        }

        input = document.querySelector("#content");
        if (input.value.trim().length < 1) {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            isValid = false;
        } else {
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
        }

        return isValid;
    }

    function setupEditHandlers() {
        // target all delete buttons
        const editButtons = document.querySelectorAll(".editPost");
        // add click handler to all delete buttons
        for (let i = 0; i < editButtons.length; i++) {
            editButtons[i].addEventListener("click", function (event) {

                // get the post id of the delete button
                const postId = parseInt(this.getAttribute("data-id"));

                loadPostIntoForm(postId);
            });
        }
    }

    function loadPostIntoForm(postId) {
        // go find the post in the posts data that matches postId
        const post = fetchPostById(postId);
        if (!post) {
            console.log("did not find post for id " + postId);
            return;
        }

        // load the post data into the form
        const titleField = document.querySelector("#title");
        const contentField = document.querySelector("#content");
        titleField.value = post.title;
        contentField.value = post.content;

        const saveButton = document.querySelector("#savePost");
        saveButton.setAttribute("data-id", postId);
    }

    function fetchPostById(postId) {
        for (let i = 0; i < posts.length; i++) {
            if (posts[i].id === postId) {
                return posts[i];
            }

        }
        // didn't find it so return something falsy
        return false;
    }

    function setupDeleteHandlers() {
        // target all delete buttons
        const deleteButtons = document.querySelectorAll(".deletePost");
        // add click handler to all delete buttons
        for (let i = 0; i < deleteButtons.length; i++) {
            deleteButtons[i].addEventListener("click", function (event) {

                // get the post id of the delete button
                const postId = this.getAttribute("data-id");
                console.log("BeforeDeleteBody id =" + postId);
                deletePost(postId);
            });
        }
    }

    function deletePost(postId) {
        const request = {
            method: "DELETE",
            headers: getHeaders(),
        }
        const url = POST_API_BASE_URL + `/${postId}`;
        fetch(url, request)
            .then(function (response) {
                if (response.status !== 200) {
                    console.log("fetch returned bad status code: " + response.status);
                    console.log(response.statusText);
                    return;
                }
                CreateView("/posts");
            });
    }

    function setupSaveHandler() {
        const saveButton = document.querySelector("#savePost");
        saveButton.addEventListener("click", function (event) {
            const postId = parseInt(this.getAttribute("data-id"));
            savePost(postId);
        });
    }

    function savePost(postId) {
        // get the title and content for the new/updated post
        const titleField = document.querySelector("#title");
        const contentField = document.querySelector("#content");
        // don't allow save if title or content are invalid
        if (!validateFields()) {
            return;
        }
        // make the new/updated post object
        const post = {
            title: titleField.value,
            content: contentField.value,
        }

        // make the request
        const request = {
            method: "POST",
            headers: getHeaders(),
            body: JSON.stringify(post)
        }
        let url = POST_API_BASE_URL;

        // if we are updating a post, change the request and the url
        if (postId > 0) {
            request.method = "PUT";
            url += `/${postId}`;
        }

        fetch(url, request)
            .then(function (response) {
                if (response.status !== 200) {
                    console.log("fetch returned bad status code: " + response.status);
                    console.log(response.statusText);
                    return;
                }
                CreateView("/posts");
            })

}