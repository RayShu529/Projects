from django.urls import path
from loginRegister import views as logviews
from . import views
from adminPage import views as adminViews
urlpatterns = [
    path('../Teacher/Homepage/', adminViews.teacherPage,name='teacherpage'),
    path('Homepage/', views.mainpage,name='mainPage'),
    path('../Login/', logviews.loginform,name='login'),
    path('Logout/', views.userlogout,name='userout'),
    path('UserProfile/', views.editProfile,name='editprofile'),
    path('UserProfile/changepass/', views.changepassword,name='changepassword'),
    path('UserProfile/deleteAccount/', views.deleteAccount,name='deleteAccount'),
    path('UserProfile/changepicture/', views.changepicture,name='changepicture'),
    path('UserProfile/changeemail/', views.changeemail,name='changeemail'),
    path('Lessons/', adminViews.getlesson,name='getlesson'),
    path('Lessons/log/', views.log,name='log'),
    path('Lessons/getlesson/', adminViews.editgetlesson,name='editgetlesson'),
    path('Quiz/getquiz/', views.getquiz,name='getquiz'),
    path('Quiz/submitquiz/', views.submitquiz,name='submitquiz'),
    path('Assessment/getass/', views.getass,name='getass'),
    path('Assessment/submitass/', views.submitass,name='submitass'),
    path('Score/getscores/', views.getscore,name='getscore'),
    path('scores/', views.getscores,name='getscores'),
    path('Certificate/', views.certificate,name='certificate'),
    path('Certificate/update/', views.certificateup,name='certificateup'),
]