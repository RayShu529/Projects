from django.db import models

# Create your models here.
class User(models.Model):
    fname = models.CharField(max_length=50, verbose_name= 'First Name', name='fname')
    lname = models.CharField(max_length=50, verbose_name= 'Last Name', name='lname')
    username = models.CharField(max_length=50,default='',verbose_name= 'Username', name='username')
    email = models.EmailField(max_length=50,default='', verbose_name= 'Email', name='email')
    password = models.CharField(max_length=50,default='', verbose_name= 'Password', name='password')
    profilepic = models.ImageField(null=True, blank=True, upload_to='images/', verbose_name='profilepic')
    certificate = models.ImageField(null=True, blank=True, upload_to='images/certificates/', verbose_name='certificate')
    datecertified = models.DateField(null=True, blank=True, verbose_name='Date of Certification')
    def __str__(self):
        return f"{self.username}"
