print("hello world");
b=10;
print(b);

print(type("hello"));
print(type(10.4*3))
print(type(print))
print(type(type))
print(type(true))
print(type(nil))
print(type(type(X)))


tabl = {key1 = "val1",key2="val2","val3"}

for k,v in pairs(tabl) do
print(k.." - "..v)
end


tabl.key1 = nil

for v,k in pairs(tabl) do
print(k.. "-" ..v)
end

print(tabl[1])



print("-------------------------------------");

print(type(X)=="nil")


html = [[
<html>
<head></head>
<body>
    <a href="http://www.runoob.com/">www.runoob.com</a>
</body>
</html>
]]
print(html)


print("a".."b")

len = "hello world"
print(#len)



function fun1(n)
    if n==0 then
return 1
else
return n* fun1(n-1)
end
end


print(fun1(5))





