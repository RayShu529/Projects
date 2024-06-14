from django.contrib import admin
from . import models
# Register your models here.
admin.site.register(models.Teacher)
admin.site.register(models.Lesson)
admin.site.register(models.QuizQuestion)
admin.site.register(models.AssessmentQuestion)