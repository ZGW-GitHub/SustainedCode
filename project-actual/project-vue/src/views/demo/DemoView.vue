<template>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column fixed prop="date" label="日期" width="150"/>
		<el-table-column prop="name" label="姓名" width="120"/>
		<el-table-column prop="state" label="状态" width="120"/>
		<el-table-column prop="city" label="城市" width="120"/>
		<el-table-column prop="address" label="地址" width="600"/>
		<el-table-column fixed="right" label="Operations" width="120">
			<template #default>
				<el-button link type="primary" size="small" @click="handleClick">详情</el-button>
				<el-button link type="primary" size="small">编辑</el-button>
			</template>
		</el-table-column>
	</el-table>
</template>

<script lang="ts" setup>

import axios from 'axios'

const handleClick = () => {
	console.log("click");

	axios.post('/gateway', {
		"api": "template.test"
	}).then((resp) => {
		console.log(resp)
	})
};

const tableData = [
	{
		date: "2016-05-03",
		name: "Tom",
		state: "California",
		city: "Los Angeles",
		address: "No. 189, Grove St, Los Angeles",
		zip: "CA 90036",
		tag: "Home",
	},
	{
		date: "2016-05-02",
		name: "Tom",
		state: "California",
		city: "Los Angeles",
		address: "No. 189, Grove St, Los Angeles",
		zip: "CA 90036",
		tag: "Office",
	},
	{
		date: "2016-05-04",
		name: "Tom",
		state: "California",
		city: "Los Angeles",
		address: "No. 189, Grove St, Los Angeles",
		zip: "CA 90036",
		tag: "Home",
	},
	{
		date: "2016-05-01",
		name: "Tom",
		state: "California",
		city: "Los Angeles",
		address: "No. 189, Grove St, Los Angeles",
		zip: "CA 90036",
		tag: "Office",
	},
];
</script>
