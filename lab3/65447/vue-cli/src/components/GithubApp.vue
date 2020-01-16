<template>
  <div id="loginForm">
    <h1>Github Repos and Followers Graph App</h1>
    <br>
    <div id="inputForm">
      <div>
        <label for="userName">UserName: </label>
        <input type="text" id="userName" name="user" v-model="user.name">
      </div>
      <br>
      <div>
        <p>Choose a number of levels: </p>
        <input type="range" id="levelsNumber" name="levels" v-model="user.levels" min="0" max="2"/>
        <label for="levelsNumber" class="ml-3">{{ user.levels }}</label>
      </div>
      <br>
      <div>
        <button @click="onSubmit()">Submit</button>
      </div>
    </div>
    <br>

    <h2>Repositories:</h2>
    <ul class="list-group text-center">
      <li class="list-group-item" v-for="(r, index) in userReturned.repos"> {{ index+1 }}. {{ r.title }}</li>
    </ul>
    <br><br>
    <h2>Graph:</h2>
    <div id="container"></div>
  </div>
</template>

<script>
  // import 'sigma/build/sigma.min.js'
  // import 'sigma-webpack/build/plugins/sigma.parsers.json.min.js'
  const sigma = require('sigma');
  (window).sigma = sigma;

  export default {
    data: function () {
      return {
        user: {
          name: '',
          levels: 0
        },
        userReturned: {
          name: '',
          levels: 0,
          repos: [],
          followers: []
        },
        s: ''
      };
    },
    methods: {
      onSubmit() {
        console.log(this.user);
        this.$http.post('http://localhost:8080/api/users', this.user, {
          emulateJSON: true,
          contentType: 'application/json; charset=UTF-8'
        })
          .then(response => {
            return response.body;
          }, error => {
            console.log(error);
          })
          .then(data => {
            this.userReturned = data;
            console.log(this.userReturned);

            this.drawGraph();
          });
        // .then(this.$http.get('http://localhost:8080/api/user')
        // .then(response => {
        //   return response.json();
        // })
        // .then(data => {
        //   this.userReturned = data;
        //   console.log(this.userReturned);
        //   this.drawGraph();
        // }));

      },
      drawGraph() {
        if(this.s !== '') {
          this.s.graph.clear();
          this.s.refresh();
        }

        this.s = new sigma('container');

        let count = -Math.floor(this.userReturned.followers.length / 2);
        let edgeIdCounter = 1;
        this.s.graph.addNode({
          id: this.userReturned.name,
          label: this.userReturned.name,
          x: 0,
          y: 0,
          size: 1.5,
          color: '#000000'
        });

        for (let follower of this.userReturned.followers) {
          this.s.graph.addNode({
            id: follower.login + this.userReturned.name,
            label: follower.login,
            x: count++,
            y: 1,
            size: 1,
            color: '#151515'
          }).addEdge({
            id: edgeIdCounter++,
            source: this.userReturned.name,
            target: follower.login + this.userReturned.name
          });

          if (this.userReturned.levels === 2) {
            let newCount = (count - 1) * 3;
            for (let inFollower of follower.followers) {
              this.s.graph.addNode({
                id: inFollower.login + follower.login,
                label: inFollower.login,
                x: newCount += 0.15,
                y: 2,
                size: 0.5,
                color: '#151515'
              }).addEdge({
                id: edgeIdCounter++,
                source: follower.login + this.userReturned.name,
                target: inFollower.login + follower.login
              })
            }
          }
        }

        // // Then, let's add some data to display:
        // s.graph.addNode({
        //   // Main attributes:
        //   id: 'n0',
        //   label: 'Hello',
        //   // Display attributes:
        //   x: 0,
        //   y: 0,
        //   size: 1,
        //   color: '#f00'
        // }).addNode({
        //   // Main attributes:
        //   id: 'n1',
        //   label: 'World !',
        //   // Display attributes:
        //   x: 1,
        //   y: 1,
        //   size: 1,
        //   color: '#00f'
        // }).addEdge({
        //   id: 'e0',
        //   // Reference extremities:
        //   source: 'n0',
        //   target: 'n1'
        // });

        // Finally, let's ask our sigma instance to refresh:
        this.s.refresh();
      }
    }
  }
</script>

<style scoped>
  div {
    margin: auto;
  }

  h1 {
    width: 300px;
    color: rgb(109, 26, 187);
    text-align: center;
    padding: 5px;
    border: 5px solid rgb(74, 74, 168);
    background: rgb(241, 210, 169);
    border-radius: 15px;
    margin-left: auto;
    margin-right: auto;
  }

  #inputForm {
    margin: auto;
    text-align: center;
    display: block;
  }

  h2 {
    margin-left: auto;
    margin-right: auto;
    text-align: center;
  }

  body {
    margin: 0;
  }

  #container {
    position: absolute;
    width: 90%;
    height: 90%;
    border: 1px solid black;
    margin-left: 90px;
    margin-bottom: 90px;
  }

</style>
