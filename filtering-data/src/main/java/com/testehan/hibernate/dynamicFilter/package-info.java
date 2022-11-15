@org.hibernate.annotations.FilterDef(
        name = "limitByReputation",
        // default condition can be overwritten
        defaultCondition = ":currentReputation <= ( select s.reputation from Student2 s where s.studentId = studentId )",
        parameters = {
                @org.hibernate.annotations.ParamDef(
                        name = "currentReputation", type = "float"
                )
        }
)

/*
The filter is inactive now; nothing indicates that it’s supposed to apply to Item
instances. You must apply and implement the filter on the classes or collections you
want to filter.
 */


package com.testehan.hibernate.dynamicFilter;

