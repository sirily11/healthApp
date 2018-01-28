# Notes for ComS 309's Project
1. Please create your own branch in the GitLab . 

    To create a new branch in Gitlab, you could make it on the website or using the following command:
    ```bash
    $ git checkout -b [name_of_your_new_branch]
    $ git push --set-upstream origin Test
    ```
    You can always check other's branch with following command:
    ```bash
    $ git branch // this will list all branches.
    ```
    And then use
    ```bash
    $ git checkout [name_of_your_new_branch]
    ```
    You can check more information [here](https://github.com/Kunena/Kunena-Forum/wiki/Create-a-new-branch-with-git-and-manage-branches)
2. Readme.md file in gitlab/github is using the markdown syntax.To get more information about markdown syntax, you can check this [link](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet). I would suggest you using Visual Studio Code to write the Readme.md file as I do. So you can get a real-time preview about what you are doing.

3. Using Socket to communicate the server and application. You can check the socket folder, there is an file called client.kt(Which is auto-convert from Java ).You can use following command to build the connection to server.
```kotlin
val socketClinet = client(address,port)
            socketClinet.sendMsg(message)
            socketClinet.closeSocket()
```
Make sure use the client in AsyncTask class.




