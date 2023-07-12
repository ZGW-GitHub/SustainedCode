<!--
  - Copyright (C) <2023> <Snow>
  -
  - This program is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - This program is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with this program.  If not, see <https://www.gnu.org/licenses/>.
  -->

<template>
  <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      status-icon
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm">

    <el-form-item label="账号：" prop="account">
      <el-input v-model="ruleForm.account"/>
    </el-form-item>

    <el-form-item label="密码：" prop="password">
      <el-input v-model="ruleForm.password" type="password" autocomplete="off"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
    </el-form-item>

  </el-form>

  <el-button type="success" @click="testRequest">testButton</el-button>

</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'
import {templateSave, templateTest} from '@/request/api'

const ruleFormRef = ref<FormInstance>()

const checkAccount = (rule: any, value: any, callback: any) => {
  if (value === '') {
    return callback(new Error('请输入账号'))
  }
  callback()
}

const checkPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    return callback(new Error('请输入密码'))
  }
  callback()
}

const ruleForm = reactive({
  account: '',
  password: '',
})

const rules = reactive<FormRules>({
  account: [{validator: checkAccount, trigger: 'blur'}],
  password: [{validator: checkPassword, trigger: 'blur'}],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!')
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const testRequest = () => {
  const data1 = templateTest({})
  console.log(data1)

  const data2 = templateSave({
    "name": "测试",
    "age": 16
  })
  console.log(data2)
}
</script>

<style lang="" scoped>

</style>