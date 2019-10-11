---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by wsail.
--- DateTime: 2019/6/26 16:23
---

mytable = {}
print("mytable 的类型是 ",type(mytable))

mytable[1] = "Lua"
mytable["wow"] = "修改前"
print("mytable 索引为1的元素是 ",mytable[1])
print("mytable 索引为wow的元素是 ",mytable["wow"])

--alternatetable 和 mytable指的是同一个table
alternatetable = mytable
print("mytable 索引为1的元素是 ",alternatetable[1])
print("mytable 索引为wow的元素是 ",alternatetable["wow"])

alternatetable["wow"] = "修改后"
print(alternatetable["wow"])

--释放变量
alternatetable = nil
print("alternatetable 是",alternatetable)

--mytable依然可以访问
print("mytable 索引为wow的元素是 ",mytable["wow"])


--Table连接

fruits = {"banana","orange","apple"}
--返回table连接后的字符串
print(table.concat(fruits))

--指定连接符
print(table.concat(fruits,","))

--指定索引来连接table
print("连接后的字符串",table.concat(fruits,",",2,3))

--末尾插入
table.insert(fruits,"mangguo")
print("索引为4的元素为 ",fruits[4])

table.insert(fruits,2,"grapes")
print("索引为2的元素为",fruits[2])

--移除最后一个元素
table.remove(fruits)
print("移除后最后一个元素为 ",fruits[5])

--排序
print(table.concat(fruits,","))
table.sort(fruits)
print(table.concat(fruits,","))

--Table最大值
function table_maxn(t)
    local mn = nil;
    for k, v in ipairs(t) do
        if(mn==nil) then
            mn = v
        end
        if mn<v then
            mn = v
        end
    end

    return mn
end

tb1 = {[1] = 2,[2]=6,[3]=34,[26]=5}
print("tb1 最大值：",table_maxn(tb1))
print("tb1 长度",#tb1)