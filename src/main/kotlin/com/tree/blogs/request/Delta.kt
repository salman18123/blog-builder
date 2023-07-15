package com.tree.blogs.request

import lombok.Builder
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable


class Delta( var type:Int, var index:Int, var paragraph:BlockRO?):Serializable {

}