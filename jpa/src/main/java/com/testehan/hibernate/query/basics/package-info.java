@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(
                name = "findStudentsOrderByMark",
                query = "select i from Student i order by i.mark desc"
        )
})



package com.testehan.hibernate.query.basics;