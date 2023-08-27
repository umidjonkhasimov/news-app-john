package uz.gita.newsapp_john.data

interface MySharedPreferences {
    var isSignedIn: Boolean
    var currentUserEmail: String?
    var currentUserPassword: String?
    var currentUserFirstName: String?
    var currentUserLastName: String?
}