import React, { Component } from "react";
import Post from "./Post";

class Posts extends Component {

    constructor(props) {
        super(props);

        this.state = {
            posts: []
        };
    }

    loadPosts() {

        fetch("https://jsonplaceholder.typicode.com/posts")
            .then((response) => response.json())
            .then((data) => {

                const posts = data.map(
                    item => new Post(item.id, item.title, item.body)
                );

                this.setState({
                    posts: posts
                });

            })
            .catch((error) => {
                throw error;
            });

    }

    componentDidMount() {

        this.loadPosts();

    }

    componentDidCatch(error) {

        alert("Error : " + error);

    }

    render() {

        return (

            <div>

                <h1>Blog Posts</h1>

                {
                    this.state.posts.map(post => (

                        <div key={post.id} className="post">

                            <h2>{post.title}</h2>

                            <p>{post.body}</p>

                            <hr />

                        </div>

                    ))
                }

            </div>

        );

    }

}

export default Posts;