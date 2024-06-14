from django.urls import path
from . import views
from userPage import views as userViews
from adminPage import views as teacherViews
urlpatterns = [
    path('Register/', views.registerform,name='registerform'),
    path('Login/', views.loginform,name='loginform'),
    path('registersave/', views.registersave,name='registersave'),
    path('usernamecheck/', views.usernamecheck,name='usernamecheck'),
    path('emailcheck/', views.emailcheck,name='emailcheck'),
    path('loginlog/', views.loginlog,name='loginlog'),
    path('logincheck/', views.logcheck,name='logincheck'),
    path('user/', userViews.mainpage,name='mainPage'),
    path('teacher/', teacherViews.teacherPage,name='teacherpage'),
]
